package ovh.nixenos.tab.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ovh.nixenos.tab.server.services.CustomUserDetailsService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    CustomUserDetailsService userService;

    public SecurityConfiguration(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        CustomUsernamePasswordAuthenticationFilter mupaf = new CustomUsernamePasswordAuthenticationFilter();
        mupaf.setAuthenticationManager(authenticationManager());

        http
        .httpBasic()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/api/heartbeat-secure").authenticated()
        .antMatchers("/login**").permitAll()
        .antMatchers("/api/users/register").permitAll()
        .anyRequest().permitAll()
        .and()
        .exceptionHandling() // 1
        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        .and()
        .addFilterAt(mupaf, UsernamePasswordAuthenticationFilter.class)
        .formLogin().loginProcessingUrl("/login")
        //.loginPage("/login")
        .successHandler(new AuthenticationSuccessHandler() {
         
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                Authentication authentication) throws IOException, ServletException {
            // run custom logics upon successful login
             
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
             
            System.out.println("The user " + username + " has logged in.");
             
            response.setStatus(200);
            response.setContentType("application/json");
            response.getWriter().print("{'status': 'authenticated'}");
            response.getWriter().flush();
        }
    }); // 1; //.antMatchers("/api/users/all", "/api/heartbeat");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override // Authentication
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(this.passwordEncoder());
   }

   @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
   @Override
   public AuthenticationManager authenticationManagerBean() throws Exception {
       return super.authenticationManagerBean();
   }

}