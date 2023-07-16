package co.com.jorge.ms.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EntityScan({
        "co.com.jorge.commons.alumnos.models.entity",
        "co.com.jorge.ms.cursos.models.entity",
        "co.com.jorge.commons.examenes.model.entity"
})
public class MsCursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCursosApplication.class, args);
    }

}
