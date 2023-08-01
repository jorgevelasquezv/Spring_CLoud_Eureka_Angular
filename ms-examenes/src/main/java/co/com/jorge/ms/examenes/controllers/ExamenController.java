package co.com.jorge.ms.examenes.controllers;

import co.com.jorge.commons.controllers.CommonController;
import co.com.jorge.commons.examenes.model.entity.Examen;
import co.com.jorge.ms.examenes.services.ExamenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService> {

    public ExamenController(ExamenService service) {
        super(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return this.validar(result);
        }
        Optional optionalExamen = service.findById(id);
        if (optionalExamen.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Examen examenDb = (Examen) optionalExamen.get();
        examenDb.setNombre(examen.getNombre());

        examenDb.getPreguntas()
                .stream()
                .filter(preguntaDb -> !examen.getPreguntas().contains(preguntaDb))
                .forEach(examenDb::removePregunta);

        examenDb.setPreguntas(examen.getPreguntas());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDb));
    }

    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filtrar(@PathVariable String term){
        return ResponseEntity.ok(service.findByNombre(term));
    }

    @GetMapping("/asignaturas")
    public ResponseEntity<?> listarAsignaturas(){
        return ResponseEntity.ok(service.findAllAsignaturas());
    }

    @GetMapping("/respondidos-por-preguntas")
    public ResponseEntity<?> obtenerExamenesIdsPorPreguntasIdsRespondidas(@RequestParam List<Long> preguntaIds){
        return ResponseEntity.ok(service.findExamenesIdConRespuestasByPreguntaIds(preguntaIds));
    }
}
