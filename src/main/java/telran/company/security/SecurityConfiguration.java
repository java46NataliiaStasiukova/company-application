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
	@Order(Ordered.LOWEST_PRECEDENCE) //lowest priority
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.csrf()
			.disable()
			.authorizeHttpRequests(requests -> 
				requests
				//TODO
				//Not working, not done yet
				
//				.requestMatchers(HttpMethod.GET).hasAuthority("ADMIN_COMPANY")
//				.requestMatchers(HttpMethod.PUT).hasAuthority("ADMIN_COMPANY")
//				.requestMatchers(HttpMethod.POST).hasAuthority("ADMIN_COMPANY")
//				.requestMatchers(HttpMethod.DELETE).hasAuthority("ADMIN_COMPANY")
				.requestMatchers("/company").hasRole("ADMIN_COMPANY")
				.requestMatchers("/company/employees/age").permitAll()
				.requestMatchers("/company/employees/month").permitAll()
				.requestMatchers("/company/employees/salary").hasAnyRole("ACCOUNTER")
				
				.anyRequest()
				.hasRole("ADMIN")
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
	   
	    return manager;
	}

	}
