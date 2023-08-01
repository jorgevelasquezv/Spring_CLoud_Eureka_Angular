package co.com.jorge.ms.examenes.services;

import co.com.jorge.commons.examenes.model.entity.Asignatura;
import co.com.jorge.commons.examenes.model.entity.Examen;
import co.com.jorge.commons.services.CommonService;

import java.util.List;

public interface ExamenService extends CommonService<Examen> {
    List<Examen> findByNombre(String term);

    List<Asignatura> findAllAsignaturas();

    Iterable<Long> findExamenesIdConRespuestasByPreguntaIds(Iterable<Long> preguntaIds);
}
