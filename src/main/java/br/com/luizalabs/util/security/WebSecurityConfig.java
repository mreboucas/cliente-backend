package br.com.luizalabs.util.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 22 de set de 2020 as 16:35:50 
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final ImplementsUserDetailService userDetailsService;
	private final static String CLIENTE_URL_BASE_RESOURCE = "/api/v1/cliente";
	private final static String PRODUTO_URL_BASE_RESOURCE = "/api/v1/produto";
	private final static String ROLE_ADMIN = "ADMIN";
	private final static String ROLE_USER = "USER";
	public WebSecurityConfig(ImplementsUserDetailService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.GET, "/").permitAll()
		.antMatchers(HttpMethod.GET, PRODUTO_URL_BASE_RESOURCE).hasAnyRole(ROLE_ADMIN, ROLE_USER)
		.antMatchers(HttpMethod.GET, CLIENTE_URL_BASE_RESOURCE).hasAnyRole(ROLE_ADMIN, ROLE_USER)
		.antMatchers(HttpMethod.POST, CLIENTE_URL_BASE_RESOURCE).hasRole(ROLE_ADMIN)
		.antMatchers(HttpMethod.PUT, CLIENTE_URL_BASE_RESOURCE).hasRole(ROLE_ADMIN)
		.antMatchers(HttpMethod.DELETE, CLIENTE_URL_BASE_RESOURCE).hasRole(ROLE_ADMIN)
		.anyRequest().authenticated()
//		.and().formLogin().permitAll()
		.and().httpBasic()
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/** Atenticação em memória
		 * auth.inMemoryAuthentication() .withUser("admin").password("admin").roles("ROLE_ADMIN");
		 */
		
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}

	/*
	 * @Override public void configure(WebSecurity web) throws Exception { web.ignoring().antMatchers("/materialize/**", "/style/**"); }
	 */

}