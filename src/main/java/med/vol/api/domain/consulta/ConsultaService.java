package med.vol.api.domain.consulta;

import med.vol.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsultas;
import med.vol.api.domain.exception.ValidacaoException;
import med.vol.api.domain.medico.Medico;
import med.vol.api.domain.medico.MedicoRepository;
import med.vol.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private List<ValidadorAgendamentoDeConsultas> validadores;

    public Page<DadosDetalhamentoConsulta> listar(AutorizacaoConsulta autorizacaoConsulta, Pageable pagina) {
//        try{
           return consultaRepository.findByLista(autorizacaoConsulta, pagina).map(consulta -> new DadosDetalhamentoConsulta(consulta));
//        }catch (Exception e){
//            throw new ValidacaoException("Erro ao listar Consultas no banco de dados");
//        }

    }

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        if(!pacienteRepository.existsById(dadosAgendamentoConsulta.idPaciente())){
            throw new ValidacaoException("O id do paciente informado não existe");
        }
        if(dadosAgendamentoConsulta.idMedico() != null && !medicoRepository.existsById(dadosAgendamentoConsulta.idMedico())){
            throw new ValidacaoException("O id do medico informado não existe");
        }
        validadores.forEach(validador -> validador.validar(dadosAgendamentoConsulta));
        var paciente = pacienteRepository.getReferenceById(dadosAgendamentoConsulta.idPaciente());
        var medico = escolherMedico(dadosAgendamentoConsulta);
        if(medico == null){
            throw new ValidacaoException("Não existe medicos disponiveis nessa data");
        }
        var consulta = new Consulta(null, medico, paciente, dadosAgendamentoConsulta.data(), medico.getEspecialidade());

        consultaRepository.save(consulta);
        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        if(dadosAgendamentoConsulta.idMedico() != null){
            return medicoRepository.getReferenceById(dadosAgendamentoConsulta.idMedico());
        }
        if(dadosAgendamentoConsulta.especialidade() == null){
            throw new ValidacaoException("Precisa informar uma especialidade caso não escolha um medico");
        }
        return medicoRepository.escolherMedicoAleatorio(dadosAgendamentoConsulta.especialidade(), dadosAgendamentoConsulta.data());
    }


    public Page<DadosDetalhamentoConsulta> detalhar(Long id, Pageable pageable) {
        try{
            return consultaRepository.findAllByMedico_Id(id, pageable).map(consulta -> new DadosDetalhamentoConsulta(consulta));
        }catch (Exception e){
            throw new ValidacaoException("Algo deu errado na hora de detalhar as consultas de cada médico no bando de dados");
        }
    }
}
