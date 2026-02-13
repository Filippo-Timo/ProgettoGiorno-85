package filippotimo.ProgettoGiorno_85.controllers;

import filippotimo.ProgettoGiorno_85.entities.Prenotazione;
import filippotimo.ProgettoGiorno_85.exceptions.ValidationException;
import filippotimo.ProgettoGiorno_85.payloads.PrenotazioneDTO;
import filippotimo.ProgettoGiorno_85.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    @Autowired
    public PrenotazioneController(PrenotazioneService service) {
        this.prenotazioneService = service;
    }

    // 1. GET /prenotazioni -> Ritorna la lista di tutte le prenotazioni

    @GetMapping
    public Page<Prenotazione> findAllPrenotazioni(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "3") int size,
                                                  @RequestParam(defaultValue = "dipendente") String orderBy,
                                                  @RequestParam(defaultValue = "asc") String sortCriteria) {
        return this.prenotazioneService.findAllPrenotazioni(page, size, orderBy, sortCriteria);
    }

    // 2. GET /prenotazioni/123 -> Ritorna una singola prenotazione

    @GetMapping("/{prenotazioneId}")
    public Prenotazione findPrenotazioneById(@PathVariable Long prenotazioneId) {
        return this.prenotazioneService.findPrenotazioneById(prenotazioneId);
    }

    // 3. POST /prenotazioni -> Crea una nuova prenotazione

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione createPrenotazione(@RequestBody @Validated PrenotazioneDTO prenotazioneDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return this.prenotazioneService.savePrenotazione(prenotazioneDTO);
        }
    }

    // 5. DELETE /prenotazioni/123 -> Cancella lo specifico viaggio

    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findDipendenteByIdAndDelete(@PathVariable Long prenotazioneId) {
        this.prenotazioneService.findByIdAdDeletePrenotazione(prenotazioneId);
    }

}
