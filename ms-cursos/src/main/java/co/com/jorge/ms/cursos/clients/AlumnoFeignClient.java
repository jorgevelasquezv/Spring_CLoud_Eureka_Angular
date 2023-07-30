package co.com.jorge.ms.cursos.clients;

import co.com.jorge.commons.alumnos.models.entity.Alumno;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-usuarios")
public interface AlumnoFeignClient {

    @GetMapping("alumnos-por-curso")
    Iterable<Alumno> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);
}
