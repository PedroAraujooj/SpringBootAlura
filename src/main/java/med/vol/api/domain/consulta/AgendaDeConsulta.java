package med.vol.api.domain.consulta;

import med.vol.api.domain.exception.ValidacaoException;
import med.vol.api.domain.medico.Medico;
import med.vol.api.domain.medico.MedicoRepository;
import med.vol.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsulta {

    @Autowired
    ConsultaRepository consultaRepository;

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    public void agendar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        if(!pacienteRepository.existsById(dadosAgendamentoConsulta.idPaciente())){
            throw new ValidacaoException("O id do paciente informado não existe");
        }
        if(dadosAgendamentoConsulta.idMedico() != null && !medicoRepository.existsById(dadosAgendamentoConsulta.idMedico())){
            throw new ValidacaoException("O id do medico informado não existe");
        }
        var paciente = pacienteRepository.getReferenceById(dadosAgendamentoConsulta.idPaciente());
        var medico = escolherMedico(dadosAgendamentoConsulta);
        var consulta = new Consulta(null, medico, paciente, dadosAgendamentoConsulta.data());

        consultaRepository.save(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        if(dadosAgendamentoConsulta.idMedico() != null){
            return medicoRepository.getReferenceById(dadosAgendamentoConsulta.idMedico());
        }
        if(dadosAgendamentoConsulta.especialidade() == null){
            throw new ValidacaoException("Precisa informar uma especialidade caso não escolha um medico");
        }
        return dadosAgendamentoConsulta.escolherMedicoAleatorio(dadosAgendamentoConsulta.especialidade(), dadosAgendamentoConsulta.data());
    }


}
