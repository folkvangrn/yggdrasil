package ovh.nixenos.tab.server.restcontrollers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartbeatController {
  @GetMapping("/api/heartbeat")
  public String heartbeat() {
    return new String("UP");
  }
  @GetMapping("/api/heartbeat-secure")
  public String secure_heartbeat() {
    return new String("SECURE UP");
  }

}
