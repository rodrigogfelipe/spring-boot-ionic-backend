package com.rodrigofelipe.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.rodrigofelipe.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	/*
	 * ("${default.sender}") e o endereço do email salvo na configuração
	 * appllication.propeties
	 */
	@Value("${default.sender}")
	private String sender;

	/* Metado sendOrderConfirmationEmail da Classe EmailService */
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}
	/* sm.setTo e o destinario do email */
	/*
	 * setFrom(sender) e o endereço do email salvo na configuração
	 * appllication.propeties
	 */
	/* setSubject e o assunto do email */
	/* setSentDate tem a função de criar a data com horario do servidor */
	/* setText tem a função do corpo do email */

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;

	}

}
