package ovh.nixenos.tab.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import ovh.nixenos.tab.server.services.CustomUserDetailsService;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    CustomUserDetailsService userService;

    public SecurityConfiguration(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
        .httpBasic()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/api/heartbeat-secure").authenticated()
        .antMatchers("/login*").permitAll()
        .antMatchers("/api/users/register").permitAll()
        .anyRequest().permitAll()
        .and()
        .exceptionHandling() // 1
        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        .and()
        .formLogin()
        .loginPage("/login"); // 1; //.antMatchers("/api/users/all", "/api/heartbeat");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override // Authentication
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(this.passwordEncoder());
   }
}