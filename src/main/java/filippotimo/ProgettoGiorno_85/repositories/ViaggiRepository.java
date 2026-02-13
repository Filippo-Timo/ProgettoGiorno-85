package filippotimo.ProgettoGiorno_85.repositories;

import filippotimo.ProgettoGiorno_85.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViaggiRepository extends JpaRepository<Viaggio, Long> {
}
