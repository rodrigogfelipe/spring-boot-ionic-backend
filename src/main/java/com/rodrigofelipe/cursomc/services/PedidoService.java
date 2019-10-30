package com.rodrigofelipe.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodrigofelipe.cursomc.domain.Pedido;
import com.rodrigofelipe.cursomc.repositories.PedidoRepository;
import com.rodrigofelipe.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private  PedidoRepository repo;

	/* SE obj ID for encontrado retorna ID, SE NAO return ObjectNotFoundException */
	public  Pedido find(Integer id) {
		Optional< Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " +  Pedido.class.getName()));
	}

}
