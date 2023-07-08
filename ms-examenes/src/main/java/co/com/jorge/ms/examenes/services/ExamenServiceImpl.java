package co.com.jorge.ms.examenes.services;

import co.com.jorge.commons.examenes.model.entity.Examen;
import co.com.jorge.commons.services.CommonServiceImpl;
import co.com.jorge.ms.examenes.model.repository.ExamenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen, ExamenRepository> implements ExamenService{

    public ExamenServiceImpl(ExamenRepository repository) {
        super(repository);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Examen> findByNombre(String term) {
        return repository.findByNombre(term);
    }
}
