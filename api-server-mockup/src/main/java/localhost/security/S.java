package localhost.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import localhost.udemy01.UserRestController;

@EnableWebSecurity
public class S extends WebSecurityConfigurerAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(S.class);
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication()
		
			.withUser("0") 
			.password("0") 
			.roles("ADMIN")
		
			.and()
			.withUser("admin") 
			.password("admin") 
			.roles("ADMIN")
			
			.and()
			.withUser("user") 
			.password("user") 
			.roles("USER")

			;
		
	}
	
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// Define URL pattern access rules, start by most restricted patterns
		// This form is cookie-based authorization; basic-authorization still pending
		
		log.debug("Alberto security configuration");
		
		http
			.authorizeRequests()
			
			.antMatchers("/admin")
			.hasRole("ADMIN")
			
			.antMatchers("/user")
			.hasAnyRole("ADMIN","USER")
			
			.antMatchers("/", "/js/**", "/css/**") // discouraged, just as example
			.permitAll()
			
			.antMatchers("/**") // recommended closing condition
			.hasRole("ADMIN")
			
			.and()
			.formLogin()
			;
		
		
		/*
		// original:
		http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
		http.formLogin();
		http.httpBasic();
		*/
		
		
	}

	
	
	
}
