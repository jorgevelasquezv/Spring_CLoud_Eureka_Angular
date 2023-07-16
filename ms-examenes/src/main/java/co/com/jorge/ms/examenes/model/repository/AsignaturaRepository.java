package co.com.jorge.ms.examenes.model.repository;

import co.com.jorge.commons.examenes.model.entity.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
}
