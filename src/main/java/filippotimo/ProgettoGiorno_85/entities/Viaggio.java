package filippotimo.ProgettoGiorno_85.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "viaggi")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Viaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    private String destinazione;

    private LocalDate dataDiPartenza;

    private String stato;


    public Viaggio(String destinazione, LocalDate dataDiPartenza) {
        this.destinazione = destinazione;
        this.dataDiPartenza = dataDiPartenza;
        this.stato = "in programma";
    }

}
