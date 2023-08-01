package co.com.jorge.ms.respuestas.models.services;

import co.com.jorge.commons.examenes.model.entity.Examen;
import co.com.jorge.commons.examenes.model.entity.Pregunta;
import co.com.jorge.ms.respuestas.clients.ExamenFeignClient;
import co.com.jorge.ms.respuestas.models.entity.Respuesta;
import co.com.jorge.ms.respuestas.models.repository.RespuestaRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RespuestaServiceImpl implements  RespuestaService{

    private RespuestaRepository respuestaRepository;

    private ExamenFeignClient examenFeignClient;

    public RespuestaServiceImpl(RespuestaRepository respuestaRepository, ExamenFeignClient examenFeignClient) {
        this.respuestaRepository = respuestaRepository;
        this.examenFeignClient = examenFeignClient;
    }

    @Override
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
        return respuestaRepository.saveAll(respuestas);
    }

    @Override
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
        Examen examen = examenFeignClient.obtenerExamenPorId(examenId);
        List<Pregunta> preguntas = examen.getPreguntas();
        List<Long> preguntaIds = preguntas.stream().map(Pregunta::getId).toList();
        List<Respuesta> respuestas = (List<Respuesta>) respuestaRepository.findRespuestaByAlumnoByPreguntaIds(alumnoId, preguntaIds);
        respuestas = respuestas.stream().map(respuesta -> {
           preguntas.forEach(pregunta -> {
               if (pregunta.getId() == respuesta.getPreguntaId()) respuesta.setPregunta(pregunta);
           });
           return respuesta;
        }).toList();
        return respuestas;
    }

    @Override
    public Iterable<Long> findExamenesIdRespondidosByAlumno(Long alumnoId) {
        List<Respuesta> respuestas = (List<Respuesta>) respuestaRepository.findByAlumnoId(alumnoId);
        List<Long> examenIds = Collections.emptyList();
        if (respuestas.size() > 0){
            List<Long> preguntaIds = respuestas.stream().map(Respuesta::getPreguntaId).toList();
            examenIds = examenFeignClient.obtenerExamenesIdsPorPreguntasIdsRespondidas(preguntaIds);
        }
        return examenIds;
    }

    @Override
    public Iterable<Respuesta> findByAlumnoId(Long id) {
        return respuestaRepository.findByAlumnoId(id);
    }
}
