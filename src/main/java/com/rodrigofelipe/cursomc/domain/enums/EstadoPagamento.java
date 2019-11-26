package com.rodrigofelipe.cursomc.domain.enums;

import com.rodrigofelipe.cursomc.domain.enums.EstadoPagamento;

public enum EstadoPagamento {

	PENDENTE(1, "Pendente"), QUITADO(2, "Quitado"), CANCELADO(3, "Cancelado");

	/* Declarando o construtor */
	private int cod;
	private String descricao;

	private EstadoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	/*
	 * Metado TipoCliente toEnum para verificar a existencia do ID caso exista
	 * retorna para o mesmo. SE NAO será lançado um EXCEPTION
	 */

	public static EstadoPagamento toEnum(Integer cod) { /* SE o cod for igual a NULL, retorn NULL */
		if (cod == null) {
			return null;
		}

		for (EstadoPagamento x : EstadoPagamento.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + cod);
	}

}