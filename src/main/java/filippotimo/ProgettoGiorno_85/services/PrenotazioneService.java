package filippotimo.ProgettoGiorno_85.services;

import filippotimo.ProgettoGiorno_85.entities.Dipendente;
import filippotimo.ProgettoGiorno_85.entities.Prenotazione;
import filippotimo.ProgettoGiorno_85.entities.Viaggio;
import filippotimo.ProgettoGiorno_85.exceptions.BadRequestException;
import filippotimo.ProgettoGiorno_85.exceptions.NotFoundException;
import filippotimo.ProgettoGiorno_85.payloads.PrenotazioneDTO;
import filippotimo.ProgettoGiorno_85.repositories.DipendentiRepository;
import filippotimo.ProgettoGiorno_85.repositories.PrenotazioniRepository;
import filippotimo.ProgettoGiorno_85.repositories.ViaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {

    private final PrenotazioniRepository prenotazioniRepository;
    private final ViaggiRepository viaggiRepository;
    private final DipendentiRepository dipendentiRepository;

    @Autowired
    public PrenotazioneService(PrenotazioniRepository prenotazioniRepository,
                               ViaggiRepository viaggiRepository,
                               DipendentiRepository dipendentiRepository) {
        this.prenotazioniRepository = prenotazioniRepository;
        this.viaggiRepository = viaggiRepository;
        this.dipendentiRepository = dipendentiRepository;
    }

    // 1. GET -> Torna una lista di Prenotazioni

    public Page<Prenotazione> findAllPrenotazioni(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.prenotazioniRepository.findAll(pageable);
    }

    // 2. GET -> Torna una singola Prenotazione specifica

    public Prenotazione findPrenotazioneById(Long prenotazioneId) {
        return this.prenotazioniRepository.findById(prenotazioneId).orElseThrow(() -> new NotFoundException(prenotazioneId));
    }

    // 3. POST -> Crea una Prenotazione

    public Prenotazione savePrenotazione(PrenotazioneDTO prenotazioneDTO) {

        Dipendente dipendente = dipendentiRepository.findById(prenotazioneDTO.dipendenteId()).orElseThrow(() -> new NotFoundException(prenotazioneDTO.dipendenteId()));
        Viaggio viaggio = viaggiRepository.findById(prenotazioneDTO.viaggioId()).orElseThrow(() -> new NotFoundException(prenotazioneDTO.dipendenteId()));

        if (prenotazioniRepository.existsByDipendenteIdAndViaggioDataDiPartenza(dipendente.getId(), viaggio.getDataDiPartenza()))
            throw new BadRequestException("Esiste già un viaggio programmato per questo utente in questa data");

        Prenotazione newPrenotazione = new Prenotazione(
                dipendente,
                viaggio,
                prenotazioneDTO.note()
        );

        Prenotazione savedPrenotazione = this.prenotazioniRepository.save(newPrenotazione);

        System.out.println("La prenotazione con id: " + savedPrenotazione.getId() + " è stata aggiunta correttamente al DB!");

        return savedPrenotazione;
    }

    // 4. DELETE -> Cancella la specifica Prenotazione

    public void findByIdAdDeletePrenotazione(Long prenotazioneId) {
        Prenotazione found = this.findPrenotazioneById(prenotazioneId);
        this.prenotazioniRepository.delete(found);
    }

}
