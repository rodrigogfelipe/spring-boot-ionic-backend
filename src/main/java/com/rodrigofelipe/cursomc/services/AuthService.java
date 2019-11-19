package com.rodrigofelipe.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rodrigofelipe.cursomc.domain.Cliente;
import com.rodrigofelipe.cursomc.repositories.ClienteRepository;
import com.rodrigofelipe.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	/* Declarando a classe ClienteRepository */
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	/* Declarando a classe EmailService */
	@Autowired
	private EmailService emailService;

	/* Declarando Random rand para gerar numeros e letras aleotorios */
	private Random rand = new Random();

	/*
	 * Metado sendNewPassword verificar a existencia do email, se caso não exista
	 * lancara um exception
	 */
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");

		}
		/* NewPass gera uma nova senha */
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));

		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);

	}

	/* Gerando uma senha de 10 carecteres (numeros é letras) */
	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();// randomChar tem a função de gerar (numeros é letras)

		}

		return new String(vet);

	}

	private char randomChar() {
		int opt = rand.nextInt(3); /* rand.nextInt(3) gerar um numeros ate 3 */
		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);

		}

		else if (opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);

		}

		else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);

		}

	}

}
