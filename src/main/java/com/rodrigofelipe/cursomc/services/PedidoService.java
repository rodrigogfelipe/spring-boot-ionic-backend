package com.rodrigofelipe.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodrigofelipe.cursomc.domain.ItemPedido;
import com.rodrigofelipe.cursomc.domain.PagamentoComBoleto;
import com.rodrigofelipe.cursomc.domain.Pedido;
import com.rodrigofelipe.cursomc.domain.enums.EstadoPagamento;
import com.rodrigofelipe.cursomc.repositories.ItemPedidoRepository;
import com.rodrigofelipe.cursomc.repositories.PagamentoRepository;
import com.rodrigofelipe.cursomc.repositories.PedidoRepository;
import com.rodrigofelipe.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoService produtoService;

	/* SE obj ID for encontrado retorna ID, SE NAO return ObjectNotFoundException */
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	/*pedido: um novo pedido a ser inserido na base de dados */
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());

		}

		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);

		}

		itemPedidoRepository.saveAll(obj.getItens());
		return obj;

	}

}
