package br.com.luizalabs.util.exceptionhandler;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 23 de set de 2020 as 09:15:27
 */

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	/**
	 * public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
	 * response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\""); response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	 * PrintWriter writer = response.getWriter(); writer.println("HTTP Status " + HttpServletResponse.SC_UNAUTHORIZED + " - " +
	 * authException.getMessage()); }
	 */
	/**
	 * public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws
	 * IOException, ServletException {
	 * 
	 * response.setContentType("application/json"); response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	 * response.getOutputStream().println("{ \"error\": \"" + authenticationException.getMessage() + "\" }");
	 * 
	 * }
	 */

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		Map<String, Object> data = new HashMap<>();
		data.put("timestamp", Calendar.getInstance().getTime());
		data.put("exception", exception.getMessage());

		response.getOutputStream().println(objectMapper.writeValueAsString(data));
	}
}
