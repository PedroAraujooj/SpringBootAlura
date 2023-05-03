package med.vol.api.domain.consulta;

import med.vol.api.domain.paciente.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>, ConsultaRepositoryQuery {
    boolean existsByMedicoIdAndData(Long aLong, LocalDateTime data);

    boolean existsByPacienteIdAndDataBetween(Long pacienteid, LocalDateTime data, LocalDateTime data2);

    Page<Consulta> findAllByMedico_Id(Long medicoIdPageable, Pageable pagina);


}
