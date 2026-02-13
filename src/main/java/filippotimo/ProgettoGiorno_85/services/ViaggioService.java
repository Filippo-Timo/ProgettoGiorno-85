package filippotimo.ProgettoGiorno_85.services;

import filippotimo.ProgettoGiorno_85.entities.Viaggio;
import filippotimo.ProgettoGiorno_85.exceptions.NotFoundException;
import filippotimo.ProgettoGiorno_85.payloads.UpdateViaggioDTO;
import filippotimo.ProgettoGiorno_85.payloads.ViaggioDTO;
import filippotimo.ProgettoGiorno_85.repositories.ViaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ViaggioService {

    private final ViaggiRepository viaggiRepository;

    @Autowired
    public ViaggioService(ViaggiRepository viaggiRepository) {
        this.viaggiRepository = viaggiRepository;
    }

    // 1. GET -> Torna una pagina di un numero definito di Viaggi

    public Page<Viaggio> findAllViaggi(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.viaggiRepository.findAll(pageable);
    }

    // 2. GET -> Torna un singolo Viaggio specifico

    public Viaggio findViaggioById(Long viaggioId) {
        return this.viaggiRepository.findById(viaggioId).orElseThrow(() -> new NotFoundException(viaggioId));
    }

    // 3. POST -> Crea un Viaggio

    public Viaggio saveViaggio(ViaggioDTO viaggioDTO) {

        Viaggio newViaggio = new Viaggio(
                viaggioDTO.destinazione(),
                viaggioDTO.dataDiPartenza()
        );

        Viaggio savedViaggio = this.viaggiRepository.save(newViaggio);

        System.out.println("Il viaggio con destinazione " + savedViaggio.getDestinazione() + " fissato per la data " + savedViaggio.getDataDiPartenza() + " è stato aggiunto correttamente al DB!");

        return savedViaggio;
    }

    // 4. PUT -> Modifica lo specifico Viaggio

    public Viaggio findByIdAndUpdateViaggio(Long viaggioId, ViaggioDTO viaggioDTO) {

        Viaggio found = this.findViaggioById(viaggioId);

        found.setDestinazione(viaggioDTO.destinazione());
        found.setDataDiPartenza(viaggioDTO.dataDiPartenza());

        Viaggio modifiedViaggio = this.viaggiRepository.save(found);

        System.out.println("Il viaggio con destinazione " + modifiedViaggio.getDestinazione() + " fissato per la data " + modifiedViaggio.getDataDiPartenza() + " è stato modificato correttamente!");

        return modifiedViaggio;
    }

    // 4. PUT -> Modifica lo stato dello specifico Viaggio

    public Viaggio findByIdAndUpdateStato(Long viaggioId, UpdateViaggioDTO updateViaggioDTO) {

        Viaggio found = this.findViaggioById(viaggioId);

        found.setStato(updateViaggioDTO.stato());

        Viaggio modifiedViaggio = this.viaggiRepository.save(found);

        System.out.println("Il viaggio con destinazione " + modifiedViaggio.getDestinazione() + " fissato per la data " + modifiedViaggio.getDataDiPartenza() + " è stato modificato correttamente!");

        return modifiedViaggio;
    }

    // 5. DELETE -> Cancella lo specifico Viaggio

    public void findByIdAdDeleteViaggio(Long viaggioId) {
        Viaggio found = this.findViaggioById(viaggioId);
        this.viaggiRepository.delete(found);
    }

}
