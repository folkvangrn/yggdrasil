package ovh.nixenos.tab.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthFilter extends BasicAuthenticationFilter {
  private static final String TOKEN_HEADER = "Authorization";
  private static final String TOKEN_PREFIX = "Bearer ";
  private final UserDetailsService userDetailsService;
  private final String secret;
  public JwtAuthFilter(AuthenticationManager authenticationManager, 
                                UserDetailsService userDetailsService,
                                String secret) {
    super(authenticationManager);
    this.userDetailsService = userDetailsService;
    this.secret = secret;
  }
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws IOException, ServletException {
    UsernamePasswordAuthenticationToken authentication = getAuthentication(request); // 1
    if (authentication == null) { 
      filterChain.doFilter(request, response);
      return;
    }
    SecurityContextHolder.getContext().setAuthentication(authentication); // 2
    filterChain.doFilter(request, response);
  }
  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(TOKEN_HEADER); // 3
    if (token != null && token.startsWith(TOKEN_PREFIX)) {
      String userName;
    try {
        userName = JWT.require(Algorithm.HMAC512(secret.getBytes("UTF-8"))) // 4
            .build()
            .verify(token.replace(TOKEN_PREFIX, "")) // 5
            .getSubject();
        if (userName != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName); // 7
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities()); // 8
      }
    } catch (JWTVerificationException | IllegalArgumentException | UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } // 6
    }
    return null;
  }
}