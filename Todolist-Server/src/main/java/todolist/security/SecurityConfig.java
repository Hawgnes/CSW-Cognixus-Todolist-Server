package todolist.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests()
				.requestMatchers("/api/v1/**").fullyAuthenticated()
				// all API require authentication to identify the user and their Todos
				.and()
				.oauth2ResourceServer().jwt()
				.and()
				.and()
				.cors().and().csrf().disable();

		return http.build();
	}
	
	/* to specify the specific password encoder 
	 * not used because currently we only allow users who sign in with Gmail
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	*/
}
