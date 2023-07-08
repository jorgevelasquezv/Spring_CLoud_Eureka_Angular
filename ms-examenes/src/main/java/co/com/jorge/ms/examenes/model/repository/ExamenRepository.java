package co.com.jorge.ms.examenes.model.repository;

import co.com.jorge.commons.examenes.model.entity.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExamenRepository extends JpaRepository<Examen, Long> {

   @Query("select e from Examen e where e.nombre like %?1%")
    List<Examen> findByNombre(String term);
}
