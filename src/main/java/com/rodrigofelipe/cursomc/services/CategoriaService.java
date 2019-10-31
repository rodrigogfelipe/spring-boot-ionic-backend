package com.rodrigofelipe.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigofelipe.cursomc.domain.Categoria;
import com.rodrigofelipe.cursomc.dto.CategoriaDTO;
import com.rodrigofelipe.cursomc.repositories.CategoriaRepository;
import com.rodrigofelipe.cursomc.services.exceptions.DataIntegrityException;
import com.rodrigofelipe.cursomc.services.exceptions.ObjectNotFoundException;

@RestController
@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	/* SE obj ID for encontrado retorna ID, SE NAO return ObjectNotFoundException */
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	/*
	 * metado Categoria para inserir no BD. ID for NULL e inserido, caso ao
	 * contrario não inserir
	 */
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	/* metado Categoria para UPDATE no BD */
	/* metado Cliente para UPDATE no BD */
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);

	}

	/* metado Categoria para DELETE no BD */
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);

		}

		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");

		}

	}

	/* metado para listar todas as Categoria BD */
	public java.util.List<Categoria> findAll() {
		return (java.util.List<Categoria>) repo.findAll();

	}

	/*
	 * Metado Page<Categoria> findPage ordenas todos os dados no BD, mostrando os
	 * números de elementos da página
	 */
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());

	}

	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setnome(obj.getnome());

	}
}
