package co.com.jorge.ms.cursos.services;

import co.com.jorge.commons.services.CommonServiceImpl;
import co.com.jorge.ms.cursos.clients.RespuestaFeignClient;
import co.com.jorge.ms.cursos.models.entity.Curso;
import co.com.jorge.ms.cursos.models.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService {

    private RespuestaFeignClient feignClient;

    public CursoServiceImpl(CursoRepository repository, RespuestaFeignClient feignClient) {
        super(repository);
        this.feignClient = feignClient;
    }

    @Override
    @Transactional(readOnly = true)
    public Curso findCursoByAlumnoId(Long id) {
        return repository.findCursoByAlumnoId(id);
    }

    @Override
    public Iterable<Long> obtenerExamenesIdConRespuestasAlumno(Long alumnoId) {
        return feignClient.obtenerExamenesIdConRespuestasAlumno(alumnoId);
    }
}
