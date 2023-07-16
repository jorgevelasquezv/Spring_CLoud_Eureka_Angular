package co.com.jorge.ms.respuestas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"co.com.jorge.ms.respuestas.models.entity",
        "co.com.jorge.commons.alumnos.models.entity",
        "co.com.jorge.commons.examenes.model.entity"})
public class MsRespuestasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsRespuestasApplication.class, args);
    }

}
