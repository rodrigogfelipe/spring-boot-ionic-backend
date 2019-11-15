package com.rodrigofelipe.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.rodrigofelipe.cursomc.security.UserSS;

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
