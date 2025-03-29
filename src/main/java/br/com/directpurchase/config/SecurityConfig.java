package br.com.directpurchase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtFilter jwtFilter;

	public SecurityConfig(JwtFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {		
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests().requestMatchers("/login/**").permitAll().anyRequest().authenticated().and()
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests((authz) -> authz
//                .anyRequest().authenticated()
//            )
//            .formLogin(withDefaults())
//            .httpBasic(withDefaults());
//        return http.build();
//    }

//    @Bean
//    public UserDetailsService inMemoryUserDetailsService(
//      PasswordEncoder passwordEncoder) {
//      UserDetails user = User.builder()
//        .username("user")
//        .password(passwordEncoder.encode("password"))
//        .roles("USER")
//        .build();
//      return new InMemoryUserDetailsManager(user);
//    }


}