package com.rodrigofelipe.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.rodrigofelipe.cursomc.domain.enums.EstadoPagamento;

@Entity
@JsonTypeName("pagamentoComBoleto") /*JsonTypeName e usado para ligar o nome lógico que a classe anotada possui*/ 
public class PagamentoComBoleto extends Pagamento {
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataVencimento;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataPagamento;

	public PagamentoComBoleto() {

	}

	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataVencimento,
			Date dataPagamento) {
		super(id, estado, pedido);
		this.dataPagamento = dataPagamento;
		this.dataVencimento = dataVencimento;

	}

	public Date getDataVencimento() {

		return dataVencimento;

	}

	public void setDataVencimento(Date dataVencimento) {

		this.dataVencimento = dataVencimento;

	}

	public Date getDataPagamento() {

		return dataPagamento;

	}

	public void setDataPagamento(Date dataPagamento) {

		this.dataPagamento = dataPagamento;

	}

}