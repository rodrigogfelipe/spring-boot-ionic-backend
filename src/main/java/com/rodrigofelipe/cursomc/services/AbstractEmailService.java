package com.rodrigofelipe.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.rodrigofelipe.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	/* @Value("${default.sender}") e o endereço do email salvo na configuração appllication.propeties*/
	@Value("${default.sender}")
	private String sender;
	
	/* Metado sendOrderConfirmationEmail da Classe EmailService*/
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {

		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail()); /*sm.setTo e o destinario do email*/
		sm.setFrom(sender);  /*setFrom(sender) e o endereço do email salvo na configuração appllication.propeties*/
		sm.setSubject("Pedido confirmado! Código: " + obj.getId()); /*setSubject e o assunto do email*/
		sm.setSentDate(new Date(System.currentTimeMillis())); /*setSentDate tem a função de criar a data com horario do servidor*/
		sm.setText(obj.toString()); /*setText tem a função do corpo do email*/
		return sm;

	}

}
