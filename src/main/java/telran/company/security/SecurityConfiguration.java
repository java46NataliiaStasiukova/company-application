package telran.company.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
@Value("${app.admin.username:admin}")
private String admin;
@Value("${app.admin.password:${ADMIN_PASSWORD}}")
private String adminPassword;

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE) 
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.csrf()
			.disable()
			.authorizeHttpRequests(requests -> 
				requests
				.requestMatchers("/company").permitAll()
				.requestMatchers(HttpMethod.POST).hasAnyRole("ADMIN", "ADMIN_COMPANY")
				.requestMatchers(HttpMethod.PUT).hasAnyRole("ADMIN", "ADMIN_COMPANY")
				.requestMatchers(HttpMethod.DELETE).hasAnyRole("ADMIN", "ADMIN_COMPANY")
				.requestMatchers("/company/employees/salary/**").hasAnyRole("ADMIN", "ACCOUNTER")
			
				.anyRequest().authenticated()
			)
			.httpBasic();
		return http.build();
			
	}
	@Bean 
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	UserDetailsManager userDetailsService(PasswordEncoder bCryptPasswordEncoder) {
	    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	    manager.createUser(User.withUsername(admin)
	      .password(bCryptPasswordEncoder.encode(adminPassword))
	      .roles("ADMIN")
	      .build());
	    
	    manager.createUser(User.withUsername("accounter@gmail.com")
	  	  .password(bCryptPasswordEncoder.encode("accounter-pass"))
	  	  .roles("ACCOUNTER")
	  	  .build());
	    
	    manager.createUser(User.withUsername("admincompany@gmail.com")
	  	  .password(bCryptPasswordEncoder.encode("admin-pass"))
	  	  .roles("ADMIN_COMPANY")
	  	  .build());
	    
	    manager.createUser(User.withUsername("user@gmail.com")
		  .password(bCryptPasswordEncoder.encode("user-pass"))
		  .roles("USER")
		  .build());
	    
	    
	   
	    return manager;
	}

	}
