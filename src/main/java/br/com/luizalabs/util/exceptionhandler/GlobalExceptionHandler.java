package br.com.luizalabs.util.exceptionhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.ServiceUnavailableException;
import javax.transaction.NotSupportedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.codec.DecodingException;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import br.com.luizalabs.util.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 19 de set de 2020 as 05:20:03
 */
@Component
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	private ObjectMapper objectMapper;
	private String fields;

	public GlobalExceptionHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

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

	private List<Erro> creatListErro(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String field = fieldError.getField();
			String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			/** String[] messages = message.split("-"); */
			/**
			 * Integer id = NumberUtils.isCreatable(messages[0]) ? Integer.valueOf(messages[0]) : 0;
			 */
			/** String msg = messages.length == 2 ? messages[1] : messages[0]; */
			Object value = fieldError.getRejectedValue();
			erros.add(new Erro(HttpStatus.BAD_REQUEST.value(), message, "O campo " + "'" + field + "'" + "recebeu o valor: " + value, field));
		}

		return erros;
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Erro> erroValidationJsonRequestList = creatListErro(ex.getBindingResult());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(erroValidationJsonRequestList);
	}

	@ExceptionHandler(value = {Throwable.class})
	public ResponseEntity<Erro> internalServerError(Throwable ex) {
		Erro responseError = new Erro();
		HttpStatus httpStatus = getHttpStatusByExceptionType(ex);
		responseError.setMessageForDeveloper(ex != null && ex.getMessage() != null ? ex.getMessage() : httpStatus.getReasonPhrase());
		responseError.setException(String.valueOf(ex));
		responseError.setStatusCode(httpStatus.value());
		return new ResponseEntity<>(responseError, httpStatus);
	}

	@ExceptionHandler(value = {BusinessException.class})
	public ResponseEntity<List<Erro>> businessValidation(BusinessException ex) {
		HttpStatus httpStatus = getHttpStatusByExceptionType(ex);
		return new ResponseEntity<>(ex.getErroList(), httpStatus);
	}

	/**
	 * @author Marcelo Rebouças 7 de mai de 2018 - 10:29:42 [marceloreboucas10@gmail.com]
	 * @return Status
	 */
	private HttpStatus getHttpStatusByExceptionType(Throwable error) {

		if (error instanceof Forbidden) {
			return HttpStatus.FORBIDDEN;
		} else if (error instanceof javax.ws.rs.NotFoundException) {
			return HttpStatus.NOT_FOUND;
		} else if (error instanceof NotSupportedException) {
			return HttpStatus.UNSUPPORTED_MEDIA_TYPE;
		} else if (error instanceof ServiceUnavailableException) {
			return HttpStatus.SERVICE_UNAVAILABLE;
		} else if (error instanceof BusinessException) {
			return HttpStatus.BAD_REQUEST;
		}

		return HttpStatus.INTERNAL_SERVER_ERROR;

	}
}
