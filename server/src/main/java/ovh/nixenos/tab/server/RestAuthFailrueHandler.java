package ovh.nixenos.tab.server;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class RestAuthFailrueHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${jwt.expirationTime}")
    private Integer expirationTime;

    @Value("${jwt.secret}")
    private String jwtSecret;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, 
                                      AuthenticationException exception) throws IOException, ServletException {
    super.onAuthenticationFailure(request, response, exception);
  }
}