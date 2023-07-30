package co.com.jorge.msusuarios.services;

import co.com.jorge.commons.alumnos.models.entity.Alumno;
import co.com.jorge.commons.services.CommonServiceImpl;
import co.com.jorge.msusuarios.client.CursoFeignClient;
import co.com.jorge.msusuarios.models.repository.AlumnoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService{

    private CursoFeignClient cursoFeignClient;
    public AlumnoServiceImpl(AlumnoRepository repository, CursoFeignClient cursoFeignClient) {
        super(repository);
        this.cursoFeignClient = cursoFeignClient;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alumno> findByNombreOrApellido(String term) {
        return repository.findByNombreOrApellido(term);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Alumno> findAllById(Iterable<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public void eliminarCursoAlumnoPorId(Long id) {
        cursoFeignClient.eliminarCursoAlumnoPorId(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        super.deleteById(id);
        this.eliminarCursoAlumnoPorId(id);
    }
}
