package med.vol.api.domain.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsultaRepositoryQuery {
    Page<Consulta> findByLista(AutorizacaoConsulta autorizacaoConsulta, Pageable pageable);
}
