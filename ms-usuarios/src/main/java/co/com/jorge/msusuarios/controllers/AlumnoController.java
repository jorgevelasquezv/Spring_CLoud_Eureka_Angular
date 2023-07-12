package co.com.jorge.msusuarios.controllers;

import co.com.jorge.commons.alumnos.models.entity.Alumno;
import co.com.jorge.commons.controllers.CommonController;
import co.com.jorge.msusuarios.services.AlumnoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService> {

    public AlumnoController(AlumnoService service) {
        super(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Alumno alumno, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return this.validar(result);
        }
        Optional<Alumno> optionalAlumno = service.findById(id);
        if (optionalAlumno.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Alumno alumnoDb = optionalAlumno.get();
        alumnoDb.setNombre(alumno.getNombre());
        alumnoDb.setApellido(alumno.getApellido());
        alumnoDb.setEmail(alumno.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
    }

    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filter(@PathVariable String term){
        return ResponseEntity.ok(service.findByNombreOrApellido(term));
    }

}
