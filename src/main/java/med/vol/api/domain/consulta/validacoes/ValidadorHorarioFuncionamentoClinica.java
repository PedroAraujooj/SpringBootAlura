package med.vol.api.domain.consulta.validacoes;

import med.vol.api.domain.consulta.DadosAgendamentoConsulta;
import med.vol.api.domain.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
@Component

public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsultas{
    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta){
        var dataConsulta = dadosAgendamentoConsulta.data();
        var ehDomingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaDaClinica = dataConsulta.getHour()<7;
        var depoisDoEncerramentoDaClinica = dataConsulta.getHour()<18;
        if(ehDomingo||antesDaAberturaDaClinica||depoisDoEncerramentoDaClinica){
            throw new ValidacaoException("Consulta fora do horario de atendimento valido");
        }
    }
}
