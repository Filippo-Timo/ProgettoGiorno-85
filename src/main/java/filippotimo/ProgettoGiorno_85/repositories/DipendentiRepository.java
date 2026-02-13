package filippotimo.ProgettoGiorno_85.repositories;

import filippotimo.ProgettoGiorno_85.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DipendentiRepository extends JpaRepository<Dipendente, Long> {
}
