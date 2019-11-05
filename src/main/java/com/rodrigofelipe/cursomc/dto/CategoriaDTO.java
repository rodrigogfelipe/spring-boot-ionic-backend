package com.rodrigofelipe.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.rodrigofelipe.cursomc.domain.Categoria;

/*DTO (Data Transfer Object), que basicamente é uma classe com atributos simples, que usamos para otimizar a comunicação entre o client e o 
 * servidor.Nossa classe DTO pode receber atributos e, assim, podemos manipulá-los da forma que quisermos.*/
public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	/*Controle de quantidade de caracteres */
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres")
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
