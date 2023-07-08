package co.com.jorge.ms.cursos.services;

import co.com.jorge.commons.services.CommonService;
import co.com.jorge.ms.cursos.models.entity.Curso;

public interface CursoService extends CommonService<Curso> {

    Curso findCursoByAlumnoId(Long id);
}
