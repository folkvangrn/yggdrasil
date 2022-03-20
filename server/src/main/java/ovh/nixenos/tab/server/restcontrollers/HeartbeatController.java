package ovh.nixenos.tab.server.restcontrollers;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartbeatController {
  @GetMapping("/heartbeat")
  public String heartbeat() {
    return new String("UP");
  }
}
