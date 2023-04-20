package med.vol.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.vol.api.domain.medico.Especialidade;
import med.vol.api.domain.medico.Medico;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        Long idMedico,

        @NotNull
        Long idPaciente,

        @NotNull
        @Future
        LocalDateTime data,

        Especialidade especialidade) {
        public Medico escolherMedicoAleatorio(Especialidade especialidade, LocalDateTime data) {

        }
}
