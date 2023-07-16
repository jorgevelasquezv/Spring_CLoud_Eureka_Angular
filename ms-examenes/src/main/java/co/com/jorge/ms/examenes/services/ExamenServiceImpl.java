package co.com.jorge.ms.examenes.services;

import co.com.jorge.commons.examenes.model.entity.Asignatura;
import co.com.jorge.commons.examenes.model.entity.Examen;
import co.com.jorge.commons.services.CommonServiceImpl;
import co.com.jorge.ms.examenes.model.repository.AsignaturaRepository;
import co.com.jorge.ms.examenes.model.repository.ExamenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen, ExamenRepository> implements ExamenService{

    private AsignaturaRepository asignaturaRepository;
    public ExamenServiceImpl(ExamenRepository repository, AsignaturaRepository asignaturaRepository) {
        super(repository);
        this.asignaturaRepository = asignaturaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Examen> findByNombre(String term) {
        return repository.findByNombre(term);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asignatura> findAllAsignaturas() {
        return asignaturaRepository.findAll();
    }
}
