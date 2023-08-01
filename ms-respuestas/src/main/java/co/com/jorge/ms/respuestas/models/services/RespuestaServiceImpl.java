package co.com.jorge.ms.respuestas.models.services;

import co.com.jorge.ms.respuestas.models.entity.Respuesta;
import co.com.jorge.ms.respuestas.models.repository.RespuestaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RespuestaServiceImpl implements  RespuestaService{

    private RespuestaRepository respuestaRepository;

    public RespuestaServiceImpl(RespuestaRepository respuestaRepository) {
        this.respuestaRepository = respuestaRepository;
    }

    @Override
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
        return respuestaRepository.saveAll(respuestas);
    }

    @Override
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
        List<Respuesta> respuestas = (List<Respuesta>) respuestaRepository.findRespuestaByAlumnoByExamen(alumnoId, examenId);
        return respuestas;
    }

    @Override
    public Iterable<Long> findExamenesIdRespondidosByAlumno(Long alumnoId) {
        List<Respuesta> respuestas = (List<Respuesta>) respuestaRepository.findExamenesIdsConRespuestasByAlumno(alumnoId);
        List<Long> examenIds = respuestas
                .stream()
                .map(respuesta -> respuesta.getPregunta().getExamen().getId())
                .distinct()
                .toList();
        return examenIds;
    }

    @Override
    public Iterable<Respuesta> findByAlumnoId(Long id) {
        return respuestaRepository.findByAlumnoId(id);
    }
}
