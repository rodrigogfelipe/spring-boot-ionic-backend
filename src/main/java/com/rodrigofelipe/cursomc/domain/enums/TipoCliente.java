package com.rodrigofelipe.cursomc.domain.enums;

public enum TipoCliente {
	/*
	 * enum não são adequados para salvar em string no BD. Salvar em codigos
	 * númericos
	 */
	PESSOAFISICA(1, "Pessoa Física"), 
	PESSOAJURIDICA(2, "Pessoa Jurídica");

	private int cod;
	private String descricao;

	private TipoCliente(int cod, String descricao) {
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
	 * /* SE o cod for igual a NULL, retorn NULL */

	public static TipoCliente toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (TipoCliente x : TipoCliente.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}

}
