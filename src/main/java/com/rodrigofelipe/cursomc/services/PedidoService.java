package com.rodrigofelipe.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rodrigofelipe.cursomc.domain.Cliente;
import com.rodrigofelipe.cursomc.domain.ItemPedido;
import com.rodrigofelipe.cursomc.domain.PagamentoComBoleto;
import com.rodrigofelipe.cursomc.domain.Pedido;
import com.rodrigofelipe.cursomc.domain.enums.EstadoPagamento;
import com.rodrigofelipe.cursomc.repositories.ItemPedidoRepository;
import com.rodrigofelipe.cursomc.repositories.PagamentoRepository;
import com.rodrigofelipe.cursomc.repositories.PedidoRepository;
import com.rodrigofelipe.cursomc.security.UserSS;
import com.rodrigofelipe.cursomc.services.exceptions.AuthorizationException;
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

	@Autowired
	ClienteService clienteService;

	@Autowired
	private EmailService emailService;

	/* SE obj ID for encontrado retorna ID, SE NAO return ObjectNotFoundException */
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));

	}

	/* pedido: um novo pedido a ser inserido na base de dados */
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
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
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);

		}

		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;

	}
	/*findPage verificar usuário esta logado, caso não estaja retornara NULL (acesso negado)*/
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");

		}

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);

	}
}