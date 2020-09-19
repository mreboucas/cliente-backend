package br.com.luizalabs.util.exception;

import org.apache.commons.lang.StringUtils;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 19 de set de 2020 as 06:22:18 
 */

public class PaginaProdutoInvaldaException extends Exception {

	private static final String MSG_EXCEPTION = "Id da página inválido. Deve ser um valor maior que 0";
	
	private static final long serialVersionUID = -5404643289799216710L;

	public PaginaProdutoInvaldaException() {
		super(MSG_EXCEPTION);
	}

	public PaginaProdutoInvaldaException(String message) {
		super(StringUtils.isNotBlank(message) ? MSG_EXCEPTION + " " + message : MSG_EXCEPTION);
	}
}
