package filippotimo.ProgettoGiorno_85.payloads;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ViaggioDTO(
        @NotBlank(message = "La destinazione è obbligatoria")
        @Size(min = 2, max = 30, message = "La destinazione deve contenere tra i 2 e i 30 caratteri")
        String destinazione,
        @NotNull(message = "La data di partenza è obbligatoria")
        @FutureOrPresent(message = "La data non può essere nel passato")
        LocalDate dataDiPartenza
) {
}
