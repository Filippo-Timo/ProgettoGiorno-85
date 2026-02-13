package filippotimo.ProgettoGiorno_85.controllers;

import filippotimo.ProgettoGiorno_85.entities.Viaggio;
import filippotimo.ProgettoGiorno_85.exceptions.ValidationException;
import filippotimo.ProgettoGiorno_85.payloads.UpdateViaggioDTO;
import filippotimo.ProgettoGiorno_85.payloads.ViaggioDTO;
import filippotimo.ProgettoGiorno_85.services.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {

    private final ViaggioService viaggioService;

    @Autowired
    public ViaggioController(ViaggioService viaggioService) {
        this.viaggioService = viaggioService;
    }

    // 1. GET /viaggi -> Ritorna la lista di tutti i viaggi divisa in pagine

    @GetMapping
    public Page<Viaggio> findAllViaggi(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "3") int size,
                                       @RequestParam(defaultValue = "destinazione") String orderBy,
                                       @RequestParam(defaultValue = "asc") String sortCriteria) {
        return this.viaggioService.findAllViaggi(page, size, orderBy, sortCriteria);
    }

    // 2. GET /viaggi/123 -> Ritorna un singolo viaggio

    @GetMapping("/{viaggioId}")
    public Viaggio findViaggiById(@PathVariable Long viaggioId) {
        return this.viaggioService.findViaggioById(viaggioId);
    }

    // 3. POST /viaggi -> Crea un nuovo viaggio

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio createViaggio(@RequestBody @Validated ViaggioDTO viaggioDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return this.viaggioService.saveViaggio(viaggioDTO);
        }
    }

    // 4. PUT /viaggi/123 -> Modifica lo specifico viaggio

    @PutMapping("/{viaggioId}")
    public Viaggio findViaggioByIdAndUpdate(@PathVariable Long viaggioId, @RequestBody @Validated ViaggioDTO viaggioDTO, BindingResult validationResult) {
        return this.viaggioService.findByIdAndUpdateViaggio(viaggioId, viaggioDTO);
    }

    // 4. PUT /viaggi/123 -> Modifica lo stato dello specifico viaggio

    @PutMapping("/{viaggioId}/stato")
    public Viaggio findViaggioByIdAndUpdateStato(@PathVariable Long viaggioId, @RequestBody @Validated UpdateViaggioDTO updateViaggioDTO, BindingResult validationResult) {
        return this.viaggioService.findByIdAndUpdateStato(viaggioId, updateViaggioDTO);
    }

    // 5. DELETE /viaggi/123 -> Cancella lo specifico viaggio

    @DeleteMapping("/{viaggioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findViaggioByIdAndDelete(@PathVariable Long viaggioId) {
        this.viaggioService.findByIdAdDeleteViaggio(viaggioId);
    }

}
