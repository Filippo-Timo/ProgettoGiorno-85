package filippotimo.ProgettoGiorno_85.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record ViaggioDTO(
        @NotBlank(message = "La destinazione è obbligatoria")
        @Size(min = 2, max = 30, message = "La destinazione deve contenere tra i 2 e i 30 caratteri")
        String destinazione,
        @NotNull(message = "La data di partenza è obbligatoria")
        @Past
        String dataDiPartenza,
        @NotBlank(message = "Lo stato è obbligatorio")
        @Size(min = 2, max = 30, message = "Lo stato deve contenere tra i 2 e i 20 caratteri")
        String stato
) {
}
