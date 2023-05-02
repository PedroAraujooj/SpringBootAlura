package med.vol.api.domain.consulta;

import med.vol.api.domain.medico.Especialidade;

public record AutorizacaoConsulta(Long idPaciente, Long idMedico, Especialidade especialidade) {
}
