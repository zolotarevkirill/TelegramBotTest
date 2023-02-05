package io.zolotarev.superbot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application-test.properties")
public class DBConfig {

    @Value("db.username")
    String dbUser;

    @Value("${db.password}")
    String dbPassword;

    @Value("${db.url}")
    String dbUrl;
}
