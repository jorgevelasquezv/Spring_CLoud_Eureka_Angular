package co.com.jorge.msusuarios.services;

import co.com.jorge.commons.alumnos.models.entity.Alumno;
import co.com.jorge.commons.services.CommonServiceImpl;
import co.com.jorge.msusuarios.models.repository.AlumnoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService{

    public AlumnoServiceImpl(AlumnoRepository repository) {
        super(repository);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alumno> findByNombreOrApellido(String term) {
        return repository.findByNombreOrApellido(term);
    }
}
