package co.com.jorge.ms.respuestas.controllers;

import co.com.jorge.ms.respuestas.models.entity.Respuesta;
import co.com.jorge.ms.respuestas.models.services.RespuestaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RespuestaController {

    private RespuestaService respuestaService;

    public RespuestaController(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Iterable<Respuesta> respuestas){
        respuestas = ((List<Respuesta>) respuestas)
                .stream()
                .map(respuesta -> {
           respuesta.setAlumnoId(respuesta.getAlumno().getId());
           return respuesta;
        }).toList();
        Iterable<Respuesta> respuestasDb = respuestaService.saveAll(respuestas);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuestasDb);
    }

    @GetMapping("/alumno/{alumnoId}/examen/{examenId}")
    public ResponseEntity<?> obtenerRespuestaPorAlumnoPorExamen(@PathVariable Long alumnoId, @PathVariable Long examenId){
        Iterable<Respuesta> respuestasDb = respuestaService.findRespuestaByAlumnoByExamen(alumnoId, examenId);
        return ResponseEntity.ok(respuestasDb);
    }

    @GetMapping("/alumno/{alumnoId}/examenes-respondidos")
    public ResponseEntity<?> obtenerExamenesIdConRespuestasAlumno(@PathVariable Long alumnoId){
        Iterable<Long> examenesIds = respuestaService.findExamenesIdRespondidosByAlumno(alumnoId);
        return ResponseEntity.ok(examenesIds);
    }
}
