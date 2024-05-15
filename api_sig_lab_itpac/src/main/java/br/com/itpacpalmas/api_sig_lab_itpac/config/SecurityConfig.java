package br.com.itpacpalmas.api_sig_lab_itpac.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import br.com.itpacpalmas.api_sig_lab_itpac.security.jwt.JwtConfigurer;
import br.com.itpacpalmas.api_sig_lab_itpac.security.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	protected void configure(HttpSecurity http) throws Exception {

		http.httpBasic().disable().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/login/**").permitAll()
				.antMatchers("/api/forgotpass/**").permitAll()
				.antMatchers("/api/manual/**").hasAnyRole("TECNICO")
				.antMatchers("/api/Agendamentos**").hasAnyRole("TECNICO")
				.antMatchers("/api/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.apply(new JwtConfigurer(tokenProvider));

	}

}
