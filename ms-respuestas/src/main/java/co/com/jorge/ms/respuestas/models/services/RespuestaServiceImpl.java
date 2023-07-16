package co.com.jorge.ms.respuestas.models.services;

import co.com.jorge.ms.respuestas.models.entity.Respuesta;
import co.com.jorge.ms.respuestas.models.repository.RespuestaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RespuestaServiceImpl implements  RespuestaService{

    private RespuestaRepository respuestaRepository;

    public RespuestaServiceImpl(RespuestaRepository respuestaRepository) {
        this.respuestaRepository = respuestaRepository;
    }

    @Override
    @Transactional
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
        return respuestaRepository.saveAll(respuestas);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
        return respuestaRepository.findRespuestaByAlumnoByExamen(alumnoId, examenId);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Long> findExamenesIdRespondidosByAlumno(Long id) {
        return respuestaRepository.findExamenesIdRespondidosByAlumno(id);
    }
}
