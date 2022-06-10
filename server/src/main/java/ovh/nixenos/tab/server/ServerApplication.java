package ovh.nixenos.tab.server;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"ovh.nixenos.tab"})
public class ServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);
  }

  //that's probably wrong place for this... BUT works
  @Bean
  public ModelMapper modelMapper(){
    return new ModelMapper();
  }
}
