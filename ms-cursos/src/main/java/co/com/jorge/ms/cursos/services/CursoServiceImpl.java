package co.com.jorge.ms.cursos.services;

import co.com.jorge.commons.alumnos.models.entity.Alumno;
import co.com.jorge.commons.services.CommonServiceImpl;
import co.com.jorge.ms.cursos.clients.AlumnoFeignClient;
import co.com.jorge.ms.cursos.clients.RespuestaFeignClient;
import co.com.jorge.ms.cursos.models.entity.Curso;
import co.com.jorge.ms.cursos.models.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService {

    private RespuestaFeignClient respuestaFeignClient;

    private AlumnoFeignClient alumnoFeignClient;

    public CursoServiceImpl(CursoRepository repository, RespuestaFeignClient respuestaFeignClient, AlumnoFeignClient alumnoFeignClient) {
        super(repository);
        this.respuestaFeignClient = respuestaFeignClient;
        this.alumnoFeignClient = alumnoFeignClient;
    }

    @Override
    @Transactional(readOnly = true)
    public Curso findCursoByAlumnoId(Long id) {
        return repository.findCursoByAlumnoId(id);
    }

    @Override
    public Iterable<Long> obtenerExamenesIdConRespuestasAlumno(Long alumnoId) {
        return respuestaFeignClient.obtenerExamenesIdConRespuestasAlumno(alumnoId);
    }

    @Override
    public Iterable<Alumno> obtenerAlumnosPorCurso(Iterable<Long> ids) {
        return alumnoFeignClient.obtenerAlumnosPorCurso(ids);
    }

    @Override
    @Transactional
    public void deleteCursoAlumnoById(Long id) {
        repository.deleteCursoAlumnoById(id);
    }
}
