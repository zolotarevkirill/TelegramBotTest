package io.zolotarev.superbot;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SuperBotApplication {
    

    public static void main(String[] args) {
        
//       SpringApplication.run(SuperBotApplication.class, args);
         SpringApplication app = new SpringApplication(SuperBotApplication.class);
         app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
         app.run(args);
    }

}
