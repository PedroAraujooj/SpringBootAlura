package med.vol.api.domain.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PacienteRepositoryQuery {
    Page<Paciente> findByLista(AutorizacaoPaciente lista, Pageable pageable);
}
