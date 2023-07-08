package co.com.jorge.ms.cursos.controllers;

import co.com.jorge.commons.alumnos.models.entity.Alumno;
import co.com.jorge.commons.controllers.CommonController;
import co.com.jorge.commons.examenes.model.entity.Examen;
import co.com.jorge.ms.cursos.models.entity.Curso;
import co.com.jorge.ms.cursos.services.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

    public CursoController(CursoService service) {
        super(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@RequestBody Curso curso, @PathVariable Long id){
        Optional<Curso> optionalCurso = service.findById(id);
        if (optionalCurso.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = optionalCurso.get();
        cursoDb.setNombre(curso.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
    }

    @PutMapping("/{id}/asignar-alumnos")
    public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id){
        Optional<Curso> optionalCurso = service.findById(id);
        if (optionalCurso.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = optionalCurso.get();
        alumnos.forEach(cursoDb::addAlumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
    }

    @PutMapping("/{id}/eliminar-alumnos")
    public ResponseEntity<?> eliminarAlumnos(@RequestBody Alumno alumno, @PathVariable Long id){
        Optional<Curso> optionalCurso = service.findById(id);
        if (optionalCurso.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = optionalCurso.get();
        cursoDb.removeAlumno(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
    }

    @GetMapping("/alumno/{id}")
    public ResponseEntity<?> buscarPorAlumnoId(@PathVariable Long id){
        return ResponseEntity.ok(service.findCursoByAlumnoId(id));
    }

    @PutMapping("/{id}/asignar-examenes")
    public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id){
        Optional<Curso> optionalCurso = service.findById(id);
        if (optionalCurso.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = optionalCurso.get();
        examenes.forEach(cursoDb::addExamen);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
    }

    @PutMapping("/{id}/eliminar-examen")
    public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id){
        Optional<Curso> optionalCurso = service.findById(id);
        if (optionalCurso.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = optionalCurso.get();
        cursoDb.removeExamen(examen);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
    }
}
