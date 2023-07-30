package co.com.jorge.ms.cursos.models.repository;

import co.com.jorge.ms.cursos.models.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("select c from Curso c join fetch c.cursoAlumnos a where a.alumnoId = ?1")
    Curso findCursoByAlumnoId(Long id);

    @Modifying
    @Query("delete from CursoAlumno ca where ca.alumnoId=?1")
    void deleteCursoAlumnoById(Long id);
}
