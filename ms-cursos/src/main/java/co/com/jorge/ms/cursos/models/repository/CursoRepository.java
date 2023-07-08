package co.com.jorge.ms.cursos.models.repository;

import co.com.jorge.ms.cursos.models.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("select c from Curso c join fetch c.alumnos a where a.id = ?1")
    Curso findCursoByAlumnoId(Long id);
}
