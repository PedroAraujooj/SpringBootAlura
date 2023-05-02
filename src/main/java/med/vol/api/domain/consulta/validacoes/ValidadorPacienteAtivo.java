package med.vol.api.domain.consulta.validacoes;

import med.vol.api.domain.consulta.DadosAgendamentoConsulta;
import med.vol.api.domain.exception.ValidacaoException;
import med.vol.api.domain.medico.MedicoRepository;
import med.vol.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsultas{
    @Autowired
    private PacienteRepository pacienteRepository;
    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta){
        if(dadosAgendamentoConsulta.idPaciente() == null){
            return;
        }
        var estaAtivo = pacienteRepository.findAtivoById(dadosAgendamentoConsulta.idPaciente());
        if(estaAtivo == 0){
            throw new ValidacaoException("O Paciente não está ativo");
        }

    }
}
