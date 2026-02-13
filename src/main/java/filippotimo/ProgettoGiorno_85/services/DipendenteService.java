package filippotimo.ProgettoGiorno_85.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import filippotimo.ProgettoGiorno_85.entities.Dipendente;
import filippotimo.ProgettoGiorno_85.exceptions.BadRequestException;
import filippotimo.ProgettoGiorno_85.exceptions.NotEmptyException;
import filippotimo.ProgettoGiorno_85.exceptions.NotFoundException;
import filippotimo.ProgettoGiorno_85.payloads.DipendenteDTO;
import filippotimo.ProgettoGiorno_85.repositories.DipendentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class DipendenteService {

    private final DipendentiRepository dipendentiRepository;
    private final Cloudinary cloudinaryUploader;

    @Autowired
    public DipendenteService(DipendentiRepository dipendentiRepository, Cloudinary cloudinaryUploader) {
        this.dipendentiRepository = dipendentiRepository;
        this.cloudinaryUploader = cloudinaryUploader;
    }

    // 1. GET -> Torna una pagina di un numero definito di Dipendenti

    public Page<Dipendente> findAllDipendenti(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.dipendentiRepository.findAll(pageable);
    }

    // 2. GET -> Torna un singolo Dipendente specifico

    public Dipendente findDipendenteById(Long dipendenteId) {
        return this.dipendentiRepository.findById(dipendenteId)
                .orElseThrow(() -> new NotFoundException(dipendenteId));
    }

    // 3. POST -> Crea un Dipendente

    public Dipendente saveDipendente(DipendenteDTO dipendenteDTO) {

        this.dipendentiRepository.findByEmail(dipendenteDTO.email()).ifPresent(dipendente -> {
            throw new BadRequestException("L'email " + dipendente.getEmail() + " è già in uso!");
        });

        Dipendente newDipendente = new Dipendente(
                dipendenteDTO.username(),
                dipendenteDTO.nome(),
                dipendenteDTO.cognome(),
                dipendenteDTO.email()
        );

        newDipendente.setAvatar("https://ui-avatars.com/api?name=" + dipendenteDTO.nome() + "+" + dipendenteDTO.cognome());

        Dipendente savedDipendente = this.dipendentiRepository.save(newDipendente);

        System.out.println("Il dipendente " + savedDipendente.getNome() + " " + savedDipendente.getCognome() + " è stato aggiunto correttamente al DB!");

        return savedDipendente;
    }

    // 4. PUT -> Modifica lo specifico Dipendente

    public Dipendente findByIdAndUpdateDipendente(Long dipendenteId, DipendenteDTO dipendenteDTO) {

        Dipendente found = this.findDipendenteById(dipendenteId);

        if (!found.getEmail().equals(dipendenteDTO.email()))
            this.dipendentiRepository.findByEmail(dipendenteDTO.email()).ifPresent(dipendente -> {
                throw new BadRequestException("L'email " + dipendente.getEmail() + " è già in uso!");
            });

        found.setUsername(dipendenteDTO.username());
        found.setNome(dipendenteDTO.nome());
        found.setCognome(dipendenteDTO.cognome());
        found.setEmail(dipendenteDTO.email());

        found.setAvatar("https://ui-avatars.com/api?name=" + dipendenteDTO.nome() + "+" + dipendenteDTO.cognome());

        Dipendente modifiedDipendente = this.dipendentiRepository.save(found);

        System.out.println("Il dipendente con id: " + modifiedDipendente.getId() + " è stato modificato con successo");

        return modifiedDipendente;
    }

    // 5. DELETE -> Cancella lo specifico Dipendente

    public void findByIdAdDeleteDipendente(Long dipendenteId) {
        Dipendente found = this.findDipendenteById(dipendenteId);
        this.dipendentiRepository.delete(found);
        System.out.println("L'utente con id: " + dipendenteId + " è stato cancellato con successo!");
    }

    // 6. PATCH -> Modifica l'avatar del dipendente in particolare

    public Dipendente findByIdAndUploadAvatar(Long dipendenteId, MultipartFile file) {

        if (file.isEmpty()) throw new NotEmptyException();

        Dipendente found = this.findDipendenteById(dipendenteId);

        try {
            Map result = cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            String imageUrl = (String) result.get("secure_url");

            found.setAvatar(imageUrl);

            return dipendentiRepository.save(found);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
