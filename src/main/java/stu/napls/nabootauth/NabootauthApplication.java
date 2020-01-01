package stu.napls.nabootauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NabootauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(NabootauthApplication.class, args);
    }

}
