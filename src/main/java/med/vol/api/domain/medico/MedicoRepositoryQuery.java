package med.vol.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicoRepositoryQuery {
    Page<Medico> findByLista(AutorizacaoMedico lista, Pageable pageable);
}
