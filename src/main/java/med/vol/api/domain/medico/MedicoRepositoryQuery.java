package med.vol.api.domain.medico;

import med.vol.api.controller.AutorizacaoListar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicoRepositoryQuery {
    Page<Medico> findByLista(AutorizacaoListar lista, Pageable pageable);
}
