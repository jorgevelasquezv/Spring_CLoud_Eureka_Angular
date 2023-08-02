package co.com.jorge.ms.respuestas.models.repository;

import co.com.jorge.ms.respuestas.models.entity.Respuesta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RespuestaRepository extends MongoRepository<Respuesta, String> {

    @Query("{'alumnoId': ?0, 'preguntaId': {$in: ?1}}")
    Iterable<Respuesta> findRespuestaByAlumnoByPreguntaIds(Long alumnoId, Iterable<Long> preguntaIds);

    Iterable<Respuesta> findByAlumnoId(Long alumnoId);

    @Query("{'alumnoId': ?0, 'pregunta.examen.id': ?1}")
    Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);

    @Query(value = "{'alumnoId': ?0}", fields = "{'pregunta.examen.id':  1}")
    Iterable<Respuesta> findExamenesIdsConRespuestasByAlumno(Long alumnoId);

}
