package co.com.jorge.ms.respuestas.models.repository;

import co.com.jorge.ms.respuestas.models.entity.Respuesta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RespuestaRepository extends MongoRepository<Respuesta, String> {

    @Query("{'alumnoId': ?0, 'preguntaId': {$in: ?1}}")
    Iterable<Respuesta> findRespuestaByAlumnoByPreguntaIds(Long alumnoId, Iterable<Long> preguntaIds);

    Iterable<Respuesta> findByAlumnoId(Long id);

}
