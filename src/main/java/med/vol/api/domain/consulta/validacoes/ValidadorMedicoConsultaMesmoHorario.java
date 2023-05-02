package med.vol.api.domain.consulta.validacoes;

import med.vol.api.domain.consulta.ConsultaRepository;
import med.vol.api.domain.consulta.DadosAgendamentoConsulta;
import med.vol.api.domain.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoConsultaMesmoHorario implements ValidadorAgendamentoDeConsultas{
    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta){
        var medicoPossuiOutraConsulta = consultaRepository.existsByMedicoIdAndData(dadosAgendamentoConsulta.idMedico(),
                dadosAgendamentoConsulta.data());
        if(medicoPossuiOutraConsulta){
            throw new ValidacaoException("Ja tem uma consulta nesse horario");
        }
    }

}
