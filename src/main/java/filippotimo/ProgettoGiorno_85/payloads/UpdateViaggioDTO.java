package filippotimo.ProgettoGiorno_85.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateViaggioDTO(
        @NotBlank(message = "Lo stato è obbligatorio")
        @Size(min = 2, max = 30, message = "Lo stato deve contenere tra i 2 e i 30 caratteri")
        String stato
) {
}
