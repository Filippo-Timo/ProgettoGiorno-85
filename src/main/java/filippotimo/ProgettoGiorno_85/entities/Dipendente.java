package filippotimo.ProgettoGiorno_85.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dipendenti")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Dipendente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    private String username;

    private String nome;

    private String cognome;

    private String email;

    private String avatar;


    public Dipendente(String username, String nome, String cognome, String email) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

}
