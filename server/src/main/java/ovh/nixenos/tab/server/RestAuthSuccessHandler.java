package ovh.nixenos.tab.server;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import ovh.nixenos.tab.server.repositories.UserRepository;
import ovh.nixenos.tab.server.users.User;

public class RestAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    //@Value("${jwt.expirationTime}")
    private Integer expirationTime = 3600000;

    //@Value("${jwt.secret}")
    private String jwtSecret = "SuPeRsEcReTsTrInG";

    private UserRepository userRepo;

    RestAuthSuccessHandler(UserRepository userRepo) {
        super();
        this.userRepo = userRepo;
    }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
                                      Authentication authentication) throws IOException, ServletException {
    clearAuthenticationAttributes(request);
      UserDetails principal = (UserDetails) authentication.getPrincipal(); // 1
  String token = JWT.create() // 2
    .withSubject(principal.getUsername()) // 3
    .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime)) // 4
    .sign(Algorithm.HMAC512(jwtSecret.getBytes("UTF-8"))); // 5
    User temporaryUser = userRepo.findByUsername(principal.getUsername());
    response.addHeader("Content-Type", "application/json");
  response.getOutputStream().print("{" + temporaryUser.jsonify().replace("{", "").replace("}", "") + ",\"token\":\"" + token + "\"}"); // 6
  response.addHeader("Authorization", "Bearer " + token);
  }
}