package com.rodrigofelipe.cursomc.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rodrigofelipe.cursomc.domain.Cidade;
import com.rodrigofelipe.cursomc.domain.Cliente;
import com.rodrigofelipe.cursomc.domain.Endereco;
import com.rodrigofelipe.cursomc.domain.enums.Perfil;
import com.rodrigofelipe.cursomc.domain.enums.TipoCliente;
import com.rodrigofelipe.cursomc.dto.ClienteDTO;
import com.rodrigofelipe.cursomc.dto.ClienteNewDTO;
import com.rodrigofelipe.cursomc.repositories.ClienteRepository;
import com.rodrigofelipe.cursomc.repositories.EnderecoRepository;
import com.rodrigofelipe.cursomc.security.UserSS;
import com.rodrigofelipe.cursomc.services.exceptions.AuthorizationException;
import com.rodrigofelipe.cursomc.services.exceptions.DataIntegrityException;
import com.rodrigofelipe.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;

	@Autowired
	private EnderecoRepository enderecoRepository;

	/* SE obj ID for encontrado retorna ID, SE NAO return ObjectNotFoundException */
	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");

		}
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	/*
	 * metado Cliente para inserir no BD. ID for NULL e inserido, caso ao contrario
	 * não inserir
	 */
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;

	}

	/* metado Cliente para UPDATE no BD */
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);

	}

	/* metado Cliente para DELETE no BD */
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);

		}

		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");

		}

	}

	/* metado para listar todas as Cliente BD */
	public java.util.List<Cliente> findAll() {
		return (java.util.List<Cliente>) repo.findAll();

	}

	/*
	 * Metado Page<Cliente> findPage ordenas todos os dados no BD, mostrando os
	 * números de elementos da página
	 */
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);

	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);/* cli.getEnderecos().add(end) o cliente reconhece o endereço */
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null) { /* getTelefone2() != null, sera adicionado */
			cli.getTelefones().add(objDto.getTelefone2());

		}

		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());

		}

		return cli;

	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());

	}
}
