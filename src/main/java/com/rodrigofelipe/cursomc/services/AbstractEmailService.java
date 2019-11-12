package com.rodrigofelipe.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.rodrigofelipe.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

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

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail()); /* sm.setTo e o destinario do email */
		sm.setFrom(sender); /* setFrom(sender) e o endereço do email salvo na configuração appllication.propeties*/
		sm.setSubject("Pedido confirmado! Código: " + obj.getId()); /* setSubject e o assunto do email */
		sm.setSentDate(new Date( System.currentTimeMillis())); /* setSentDate tem a função de criar a data com horario do servidor */
		sm.setText(obj.toString()); /* setText tem a função do corpo do email */
		return sm;

	}

	/* htmlFromTemplatePedido será responsável por retornar o HTML preenchido com os dados de um pedido, a partir do template Thymeleaf */
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		return templateEngine.process("email/confirmacaoPedido", context);

	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
			
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(obj);
		}

	}

	private MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
		mmh.setSentDate(new Date(
				System.currentTimeMillis())); /* setSentDate tem a função de criar a data com horario do servidor */
		mmh.setText(htmlFromTemplatePedido(obj), true); /* setText tem a função do corpo do email */
		return mimeMessage;
	}

}
