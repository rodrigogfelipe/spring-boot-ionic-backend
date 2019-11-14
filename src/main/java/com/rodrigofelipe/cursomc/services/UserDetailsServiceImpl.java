package com.rodrigofelipe.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rodrigofelipe.cursomc.domain.Cliente;
import com.rodrigofelipe.cursomc.repositories.ClienteRepository;
import com.rodrigofelipe.cursomc.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	/* Declarando a Classe ClienteRepository */
	@Autowired
	private ClienteRepository repo;

	/*
	 * Metado loadUserByUsername caso email exista retorna Id, Email, Senha. Caso
	 * seja igual a NULL lança uma exceção
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Cliente cli = repo.findByEmail(email);
		if (cli == null) {
			throw new UsernameNotFoundException(email);

		}

		return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());

	}

}