package br.com.luizalabs.util.exceptionhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.codec.DecodingException;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 19 de set de 2020 as 05:20:03 
 */
@Component
@ControllerAdvice
@Slf4j
public class RestWebExceptionHandler implements WebExceptionHandler {

	private ObjectMapper objectMapper;
	private String fields;

	public RestWebExceptionHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		if (ex instanceof WebExchangeBindException) {
			WebExchangeBindException webExchangeBindException = (WebExchangeBindException) ex;


			List<Erro> errors = new ArrayList<>();
			webExchangeBindException.getFieldErrors().forEach(e -> errors.add(new Erro(e.getDefaultMessage(), e.getCode(), e.getField())));

			try {
				exchange.getResponse().setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY);
				exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

				DataBuffer db = new DefaultDataBufferFactory().wrap(objectMapper.writeValueAsBytes(errors));

				// write the given data buffer to the response
				// and return a Mono that signals when it's done
				return exchange.getResponse().writeWith(Mono.just(db));

			} catch (JsonProcessingException e) {
				log.error(e.getMessage());
				return Mono.empty();
			}
		}
		return Mono.error(ex);
	}


	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DecodingException.class)
	@ResponseBody
	protected Erro handleHttpMessageNotReadable(DecodingException ex) {
		if (ex.getCause() instanceof InvalidFormatException) {
			InvalidFormatException res = (InvalidFormatException) ex.getCause();
			if (ex.getCause() instanceof JsonMappingException) {
				JsonMappingException result = (JsonMappingException) ex.getCause();
				this.fields = result.getPath().stream().map(Reference::getFieldName).collect(Collectors.joining("."));
				String messageForDeveloper = ex.getCause().getLocalizedMessage();
				return new Erro("Mensagem invalida: " + res.getValue(), messageForDeveloper, fields);
			}
		}

		if (ex.getCause() instanceof JsonMappingException) {
			JsonMappingException result = (JsonMappingException) ex.getCause();
			this.fields = result.getPath().stream().map(Reference::getFieldName).collect(Collectors.joining("."));
			return new Erro(result.getOriginalMessage(), ex.getCause().getMessage(), fields);
		}

		return new Erro("", ex.getCause().getMessage(), fields);
	}


	/*
	 * @ResponseStatus(code=HttpStatus.BAD_REQUEST)
	 * 
	 * @ExceptionHandler({ObjectMessageException.class})
	 * 
	 * @ResponseBody public Erro handlePessoaInexistenteException(ObjectMessageException ex) { String messageForUser = ex.getMessage(); String
	 * messageForDeveloper = ex.toString(); return new Erro(messageForUser, messageForDeveloper,ex.getField()); }
	 */
}
