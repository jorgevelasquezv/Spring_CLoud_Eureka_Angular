package co.com.jorge.ms.examenes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"co.com.jorge.commons.examenes.model.entity"})
public class MsExamenesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsExamenesApplication.class, args);
    }

}
