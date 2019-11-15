package com.rodrigofelipe.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rodrigofelipe.cursomc.domain.Cliente;
import com.rodrigofelipe.cursomc.domain.Pedido;

/*CategoriaRepository acessar os dados  SALVAR, CONSULTAR no BD do obj classe Categoria */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	/*findByCliente buscar pedidos por clientes*/
	@Transactional(readOnly=true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);

}
