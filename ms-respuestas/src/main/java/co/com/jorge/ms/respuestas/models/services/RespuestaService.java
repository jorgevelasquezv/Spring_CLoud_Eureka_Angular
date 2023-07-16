package co.com.jorge.ms.respuestas.models.services;

import co.com.jorge.ms.respuestas.models.entity.Respuesta;

public interface RespuestaService {

    Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);

    Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);

    Iterable<Long> findExamenesIdRespondidosByAlumno(Long id);
}
