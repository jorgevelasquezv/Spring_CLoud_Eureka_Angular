package co.com.jorge.ms.examenes.controllers;

import co.com.jorge.commons.controllers.CommonController;
import co.com.jorge.commons.examenes.model.entity.Examen;
import co.com.jorge.ms.examenes.services.ExamenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService> {

    public ExamenController(ExamenService service) {
        super(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Examen examen, @PathVariable Long id){
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
}