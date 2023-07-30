package co.com.jorge.ms.cursos.controllers;

import co.com.jorge.commons.alumnos.models.entity.Alumno;
import co.com.jorge.commons.controllers.CommonController;
import co.com.jorge.commons.examenes.model.entity.Examen;
import co.com.jorge.ms.cursos.models.entity.Curso;
import co.com.jorge.ms.cursos.models.entity.CursoAlumno;
import co.com.jorge.ms.cursos.services.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

    @Value("${config.balanceador.test}")
    private String balanceadorTest;
    public CursoController(CursoService service) {
        super(service);
    }

    @GetMapping
    @Override
    public ResponseEntity<?> listar(){
        List<Curso> cursos = ((List<Curso>) service.findAll())
                .stream()
                .map(curso -> {
                    curso.getCursoAlumnos()
                            .forEach(cursoAlumno -> {
                                Alumno alumno = new Alumno();
                                alumno.setId(cursoAlumno.getAlumnoId());
                                curso.addAlumno(alumno);
                            });
                    return curso;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/pagina")
    @Override
    public ResponseEntity<?> listar(Pageable pageable){
        Page<Curso> page = service.findAll(pageable).map(curso -> {
            curso.getCursoAlumnos()
                    .forEach(cursoAlumno -> {
                        Alumno alumno = new Alumno();
                        alumno.setId(cursoAlumno.getAlumnoId());
                        curso.addAlumno(alumno);
                    });
            return curso;
        });
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("totalElements", page.getTotalElements());
        pageData.put("totalPages", page.getTotalPages());
        pageData.put("size", page.getSize());
        pageData.put("number", page.getNumber());
        Map<String, Object> body = new HashMap<>();
        body.put("data", page.getContent());
        body.put("page", pageData);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/eliminar-alumno/{id}")
    public ResponseEntity<?> eliminarCursoAlumnoPorId(@PathVariable Long id){
        service.deleteCursoAlumnoById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> ver(@PathVariable Long id){
        Optional<Curso> optionalCurso = service.findById(id);
        if (optionalCurso.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Curso curso = optionalCurso.get();
        if (!curso.getCursoAlumnos().isEmpty()){
            List<Long> ids = curso.getCursoAlumnos().stream().map(CursoAlumno::getAlumnoId).toList();
            List<Alumno> alumnos = (List<Alumno>) service.obtenerAlumnosPorCurso(ids);
            curso.setAlumnos(alumnos);
        }
        return ResponseEntity.ok(curso);
    }

   @GetMapping("/balanceador-test")
    public ResponseEntity<?> balanceadorTest() {
       Map<String, Object> body = new HashMap<>();
       body.put("balanceador", balanceadorTest);
       body.put("cursos", service.findAll());
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return this.validar(result);
        }
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
        alumnos.forEach(alumno -> {
            CursoAlumno cursoAlumno = new CursoAlumno();
            cursoAlumno.setAlumnoId(alumno.getId());
            cursoAlumno.setCurso(cursoDb);
            cursoDb.addCursoAlumno(cursoAlumno);
        });
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
    }

    @PutMapping("/{id}/eliminar-alumnos")
    public ResponseEntity<?> eliminarAlumnos(@RequestBody Alumno alumno, @PathVariable Long id){
        Optional<Curso> optionalCurso = service.findById(id);
        if (optionalCurso.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = optionalCurso.get();
        CursoAlumno cursoAlumno = new CursoAlumno();
        cursoAlumno.setAlumnoId(alumno.getId());
        cursoDb.removeCursoAlumno(cursoAlumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
    }

    @GetMapping("/alumno/{id}")
    public ResponseEntity<?> buscarPorAlumnoId(@PathVariable Long id){
        Curso curso = service.findCursoByAlumnoId(id);
        if (curso != null){
            List<Long> examenesIds = (List<Long>) service.obtenerExamenesIdConRespuestasAlumno(id);

            List<Examen> examenes = curso.getExamenes().stream().map(examen -> {
                if (examenesIds.contains(examen.getId())) examen.setRespondido(true);
                return examen;
            }).collect(Collectors.toList());

            curso.setExamenes(examenes);
        }
        return ResponseEntity.ok(curso);
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
