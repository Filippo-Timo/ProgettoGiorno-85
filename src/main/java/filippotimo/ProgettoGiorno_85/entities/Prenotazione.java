package filippotimo.ProgettoGiorno_85.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "prenotazioni")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;

    @ManyToOne
    @JoinColumn(name = "viaggio_id")
    private Viaggio viaggio;

    private LocalDate dataDiRichiesta;

    private String note;


    public Prenotazione(Dipendente dipendente, Viaggio viaggio, String note) {
        this.dipendente = dipendente;
        this.viaggio = viaggio;
        this.dataDiRichiesta = LocalDate.now();
        this.note = note;
    }

}
