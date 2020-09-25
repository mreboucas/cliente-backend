package br.com.luizalabs.util.exception;

import java.util.ArrayList;
import java.util.List;
import br.com.luizalabs.util.exceptionhandler.Erro;
import lombok.Getter;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 22 de set de 2020 as 09:22:18
 */
@Getter
public class BusinessException extends Exception {

	private static final long serialVersionUID = -5404643289799216710L;

	private final List<Erro> erroList = new ArrayList<>();

	public BusinessException() {}

	public BusinessException(Erro erro) {
		this.erroList.add(erro);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(List<Erro> erroList) {
		this.erroList.addAll(erroList);
	}

}
