package com.rodrigofelipe.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rodrigofelipe.cursomc.domain.Pedido;

/*CategoriaRepository acessar os dados  SALVAR, CONSULTAR no BD do obj classe Categoria */

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
