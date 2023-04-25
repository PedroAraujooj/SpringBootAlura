package med.vol.api.domain.consulta.validacoes;

import med.vol.api.domain.consulta.ConsultaRepository;
import med.vol.api.domain.consulta.DadosAgendamentoConsulta;
import med.vol.api.domain.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class ValidarPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsultas{
    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta){
        var primeiroHorario = dadosAgendamentoConsulta.data().withHour(7);
        var ultimoHorario = dadosAgendamentoConsulta.data().withHour(18);
        var pacientePossuiOutraConsultaNoDia = consultaRepository.existsByPacienteIdAndDataBetween(dadosAgendamentoConsulta.idPaciente() ,primeiroHorario,
                ultimoHorario);
        if (pacientePossuiOutraConsultaNoDia){
            throw new ValidacaoException("O paciente ja tem uma consulta nesse dia");
        }

    }
}
