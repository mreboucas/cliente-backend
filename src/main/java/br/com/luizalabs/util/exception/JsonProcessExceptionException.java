package br.com.luizalabs.util.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 19 de set de 2020 as 06:22:18 
 */

public class JsonProcessExceptionException extends Exception {

	private static final String MSG_EXCEPTION = "Erro ao tentar realizar o parse do json para o objeto.\n";
	
	private static final long serialVersionUID = -5404643289799216710L;

	public JsonProcessExceptionException() {
		super(MSG_EXCEPTION);
	}

	public JsonProcessExceptionException(String message) {
		super(StringUtils.isNotBlank(message) ? MSG_EXCEPTION + " " + message : MSG_EXCEPTION);
	}
}
