package com.rodrigofelipe.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.rodrigofelipe.cursomc.domain.Cliente;
import com.rodrigofelipe.cursomc.domain.enums.TipoCliente;
import com.rodrigofelipe.cursomc.dto.ClienteNewDTO;
import com.rodrigofelipe.cursomc.repositories.ClienteRepository;
import com.rodrigofelipe.cursomc.resources.exception.FieldMessage;
import com.rodrigofelipe.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repo;

	@Override
	public void initialize(ClienteInsert ann) {

	}

	/*
	 * Metado isValid verificar as validações dos campos preenchidos TRUE for
	 * valido, FALSE não valido. SE TipoCliente for diferente isValidCPF, CPF será
	 * inválido. /*Validação do email caso já existente
	 */
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));

		}

		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));

		}

		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();

		}

		return list.isEmpty();

	}

}
