package com.rodrigofelipe.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rodrigofelipe.cursomc.domain.Cliente;
import com.rodrigofelipe.cursomc.dto.ClienteDTO;
import com.rodrigofelipe.cursomc.repositories.ClienteRepository;
import com.rodrigofelipe.cursomc.services.exceptions.DataIntegrityException;
import com.rodrigofelipe.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	/* SE obj ID for encontrado retorna ID, SE NAO return ObjectNotFoundException */
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	/*
	 * metado Cliente para inserir no BD. ID for NULL e inserido, caso ao contrario
	 * não inserir
	 */
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return repo.save(obj);
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
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);

	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());

	}
}
