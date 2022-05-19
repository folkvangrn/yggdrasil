package ovh.nixenos.tab.server;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import ovh.nixenos.tab.server.repositories.UserRepository;
import ovh.nixenos.tab.server.services.CustomUserDetailsService;
import ovh.nixenos.tab.server.services.UserService;
import ovh.nixenos.tab.server.users.User;

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

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;

    CustomUserDetailsService userService;

    private Integer expirationTime;
    private String jwtSecret;

    public SecurityConfiguration(CustomUserDetailsService userService,
            @Value("${jwt.expiration-time}") Integer expirationTime, @Value("${jwt.secret-string}") String jwtSecret) {
        this.userService = userService;
        this.expirationTime = expirationTime;
        this.jwtSecret = jwtSecret;
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
                .antMatchers("/api/login**").permitAll()
                .antMatchers("/api/users").hasAuthority("admin")
                .antMatchers("/api/users/managers").hasAuthority("manager")
                .antMatchers("/api/users/workers").hasAuthority("manager")
                .antMatchers("/api/users/workers").hasAuthority("worker")
                .antMatchers("/api/clients**").hasAuthority("manager")
                .antMatchers("/api/vehicles**").hasAuthority("manager")
                .antMatchers("/api/requests**").hasAuthority("manager")
                .antMatchers("/api/requests/**/activities").hasAuthority("worker")
                .antMatchers("/api/activities**").hasAuthority("worker")
                .antMatchers("/api/activities**").hasAuthority("manager")
                .antMatchers("/api/activity-dictionary**").hasAuthority("worker")
                .antMatchers("/api/activity-dictionary**").hasAuthority("manager")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginProcessingUrl("/api/login")
                .loginPage("/api/login")
                .and()
                .addFilter(new JwtAuthFilter(authenticationManager(), super.userDetailsService(), jwtSecret));
    }

    @Bean
    public JsonObjectAuthenticationFilter authenticationFilter() throws Exception {
        JsonObjectAuthenticationFilter filter = new JsonObjectAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(
                new RestAuthSuccessHandler(this.userRepo, this.expirationTime, this.jwtSecret, this.modelMapper)); // 1
        filter.setAuthenticationFailureHandler(new RestAuthFailrueHandler()); // 2
        filter.setAuthenticationManager(super.authenticationManager()); // 3
        return filter;
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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                Arrays.asList("http://127.0.0.1:3000", "http://127.0.0.1:8080", "http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}