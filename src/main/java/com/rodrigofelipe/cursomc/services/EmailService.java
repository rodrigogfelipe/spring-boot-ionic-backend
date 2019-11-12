package com.rodrigofelipe.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.rodrigofelipe.cursomc.domain.Pedido;

public interface EmailService {
	
	/*sendOrderConfirmationEmail é a confirmação do email*/
	void sendOrderConfirmationEmail(Pedido obj);
	/*sendEmail tenha a função de enviar o email */
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Pedido obj); 
	/*MimeMessage enviar email em HTML*/
	void sendHtmlEmail(MimeMessage msg); 
}
