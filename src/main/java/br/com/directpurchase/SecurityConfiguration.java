package br.com.directpurchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.com.directpurchase.auth.DirectPurchaseAuthentication;
import br.com.directpurchase.config.BasicAuthFilter;
import br.com.directpurchase.config.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private DirectPurchaseAuthentication directPurchaseAuthentication;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				authorizeRequests -> authorizeRequests.requestMatchers("/").permitAll().anyRequest().authenticated())
				.httpBasic(httpb -> httpb.authenticationEntryPoint(new RestAuthenticationEntryPoint()));
		http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(customBasicAuthFilter(), BasicAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	BasicAuthFilter customBasicAuthFilter() {
		return new BasicAuthFilter(directPurchaseAuthentication);
	}
}
