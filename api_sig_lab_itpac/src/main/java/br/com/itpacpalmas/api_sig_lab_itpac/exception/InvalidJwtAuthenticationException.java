package br.com.itpacpalmas.api_sig_lab_itpac.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidJwtAuthenticationException extends AuthenticationException{

	private static final long serialVersionUID = 1L;
	
	public InvalidJwtAuthenticationException(String exception) {
		super(exception);
	}
	
}
