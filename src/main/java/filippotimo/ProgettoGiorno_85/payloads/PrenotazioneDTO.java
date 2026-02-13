package filippotimo.ProgettoGiorno_85.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PrenotazioneDTO(
        @NotNull(message = "L'ID del dipendente è obbligatorio")
        Long dipendenteId,
        @NotNull(message = "L'ID del viaggio è obbligatorio")
        Long viaggioId,
        @NotBlank(message = "La data di richiesta è obbligatoria")
        LocalDate dataDiRichiesta,
        @Size(min = 2, max = 50, message = "Il nome deve contenere tra i 2 e i 30 caratteri")
        String note
) {
}
