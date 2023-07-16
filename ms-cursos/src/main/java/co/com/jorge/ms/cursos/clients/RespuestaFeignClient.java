package co.com.jorge.ms.cursos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-respuestas")
public interface RespuestaFeignClient {

    @GetMapping("/alumno/{alumnoId}/examenes-respondidos")
    Iterable<Long> obtenerExamenesIdConRespuestasAlumno(@PathVariable Long alumnoId);
}
