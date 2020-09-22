package br.com.luizalabs.util.exception;

import java.util.List;
import br.com.luizalabs.util.exceptionhandler.Erro;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 21 de set de 2020 as 19:22:01 
 */
@Getter
@Setter
public class ProdutoDuplicadoException extends Exception {
	
	private static final long serialVersionUID = -5404643289799216710L;
	
	private final List<Erro> erroList;

	public ProdutoDuplicadoException() {
		this.erroList = null;
	}

	public ProdutoDuplicadoException(String message) {
		super(message);
		this.erroList = null;
	}

	public ProdutoDuplicadoException(List<Erro> erroList) {
		super();
		this.erroList = erroList;
	}

}
