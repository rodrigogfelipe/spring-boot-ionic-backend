package com.rodrigofelipe.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.rodrigofelipe.cursomc.security.UserSS;
/*UserService com um método que retorna o usuário logado */
public class UserService {
	
	/*Metado UserSS authenticated verifica qual usuário que esta logado no sistema */
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;

		}

	}
}
