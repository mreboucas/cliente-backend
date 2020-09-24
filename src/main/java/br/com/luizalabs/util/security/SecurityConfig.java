package br.com.luizalabs.util.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import br.com.luizalabs.util.exceptionhandler.CustomAuthenticationFailureHandler;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 22 de set de 2020 as 16:35:50
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final ImplementsUserDetailService userDetailsService;
	/**
	private static final String CLIENTE_URL_BASE_RESOURCE = "/api/v1/cliente";
	private static final String PRODUTO_URL_BASE_RESOURCE = "/api/v1/produto";
	private static final  String ROLE_ADMIN = "ADMIN";
	private static final String ROLE_USER = "USER";
	*/
	public SecurityConfig(ImplementsUserDetailService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			//.antMatchers(HttpMethod.GET, "/**").permitAll()
			.anyRequest().authenticated()
			.and().httpBasic();
		
//		http.csrf().disable()
//				.authorizeRequests()
//		      /** .antMatchers(HttpMethod.GET, "/").permitAll() */
//				.antMatchers(HttpMethod.GET, PRODUTO_URL_BASE_RESOURCE).hasAnyRole(ROLE_ADMIN, ROLE_USER)
//		      .antMatchers(HttpMethod.GET, CLIENTE_URL_BASE_RESOURCE).hasAnyRole(ROLE_ADMIN, ROLE_USER)
//		      .antMatchers(HttpMethod.POST, CLIENTE_URL_BASE_RESOURCE).hasRole(ROLE_ADMIN)
//		      .antMatchers(HttpMethod.PUT, CLIENTE_URL_BASE_RESOURCE).hasRole(ROLE_ADMIN)
//		      .antMatchers(HttpMethod.DELETE, CLIENTE_URL_BASE_RESOURCE).hasRole(ROLE_ADMIN)
//		      .anyRequest().authenticated() 
//		      /** .and().formLogin().permitAll() */
//		      .and().httpBasic();
//			   /** .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")); */

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/** Atenticação em memória */
		/**
		 * auth.inMemoryAuthentication() .withUser("admin").password("{noop}admin").roles(ROLE_ADMIN, ROLE_USER) .and()
		 * .withUser("user").password("{noop}user").roles(ROLE_USER);
		 */

		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

	}
	
	 @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

	/*
	 * @Override public void configure(WebSecurity web) throws Exception { web.ignoring().antMatchers("/materialize/**", "/style/**"); }
	 */

}
