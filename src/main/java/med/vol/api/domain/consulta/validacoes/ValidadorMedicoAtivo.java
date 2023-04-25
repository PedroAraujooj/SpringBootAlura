package med.vol.api.domain.consulta.validacoes;

import med.vol.api.domain.consulta.DadosAgendamentoConsulta;
import med.vol.api.domain.exception.ValidacaoException;
import med.vol.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsultas {

    @Autowired
    private MedicoRepository medicoRepository;
    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta){
        if(dadosAgendamentoConsulta.idMedico() == null){
            return;
        }
        var estaAtivo = medicoRepository.findAtivoById(dadosAgendamentoConsulta.idMedico());
        if(!estaAtivo){
            throw new ValidacaoException("O medico não está ativo");
        }

    }
}
