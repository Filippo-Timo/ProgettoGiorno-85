package filippotimo.ProgettoGiorno_85.repositories;

import filippotimo.ProgettoGiorno_85.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, Long> {
    boolean existsByDipendenteIdAndViaggioDataDiPartenza(Long dipendenteId, LocalDate dataDiPartenza);
}
