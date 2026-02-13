package filippotimo.ProgettoGiorno_85.controllers;

import filippotimo.ProgettoGiorno_85.entities.Dipendente;
import filippotimo.ProgettoGiorno_85.exceptions.ValidationException;
import filippotimo.ProgettoGiorno_85.payloads.DipendenteDTO;
import filippotimo.ProgettoGiorno_85.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    private final DipendenteService dipendenteService;

    @Autowired
    public DipendenteController(DipendenteService dipendenteService) {
        this.dipendenteService = dipendenteService;
    }

    // 1. GET /dipendenti -> Ritorna la lista di tutti i dipendenti divisa in pagine

    @GetMapping
    public Page<Dipendente> findAllDipendenti(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "3") int size,
                                              @RequestParam(defaultValue = "nome") String orderBy,
                                              @RequestParam(defaultValue = "asc") String sortCriteria) {
        return this.dipendenteService.findAllDipendenti(page, size, orderBy, sortCriteria);
    }

    // 2. GET /dipendenti/123 -> Ritorna un singolo dipendente

    @GetMapping("/{dipendenteId}")
    public Dipendente findDipendenteById(@PathVariable Long dipendenteId) {
        return this.dipendenteService.findDipendenteById(dipendenteId);
    }

    // 3. POST /dipendenti -> Crea un nuovo dipendente

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente createDipendente(@RequestBody @Validated DipendenteDTO dipendenteDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return this.dipendenteService.saveDipendente(dipendenteDTO);
        }
    }

    // 4. PUT /dipendenti/123 -> Modifica lo specifico dipendenti

    @PutMapping("/{dipendenteId}")
    public Dipendente findDipendenteByIdAndUpdate(@PathVariable Long dipendenteId, @RequestBody @Validated DipendenteDTO dipendenteDTO, BindingResult validationResult) {
        return this.dipendenteService.findByIdAndUpdateDipendente(dipendenteId, dipendenteDTO);
    }

    // 5. DELETE /dipendenti/123 -> Cancella lo specifico dipendente

    @DeleteMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findDipendenteByIdAndDelete(@PathVariable Long dipendenteId) {
        this.dipendenteService.findByIdAdDeleteDipendente(dipendenteId);
    }

    // 6. PATCH /123/avatar -> Modifica l'avatar dell'dipendente in particolare

    @PatchMapping("/{dipendenteId}/avatar")
    public Dipendente uploadImage(@RequestParam("profile_picture") MultipartFile file, @PathVariable Long dipendenteId) {

        Dipendente dipendenteModified = this.dipendenteService.findByIdAndUploadAvatar(dipendenteId, file);

        return dipendenteModified;
    }

}
