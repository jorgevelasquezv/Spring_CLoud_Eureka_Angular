package co.com.jorge.msusuarios.services;


import co.com.jorge.commons.alumnos.models.entity.Alumno;
import co.com.jorge.commons.services.CommonService;

import java.util.List;

public interface AlumnoService extends CommonService<Alumno> {

    List<Alumno> findByNombreOrApellido(String term);

    Iterable<Alumno> findAllById(Iterable<Long> ids);

    void eliminarCursoAlumnoPorId(Long id);
}
