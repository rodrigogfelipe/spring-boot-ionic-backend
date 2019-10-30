package com.rodrigofelipe.cursomc.dto;

import java.io.Serializable;

import com.rodrigofelipe.cursomc.domain.Categoria;

public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	
	/*Declarando construtor padrao*/
	public CategoriaDTO() {

	}
	
	public CategoriaDTO(Categoria obj) {
		id = obj.getId();
		nome = obj.getnome();
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
