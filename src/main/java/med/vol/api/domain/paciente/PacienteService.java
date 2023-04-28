package med.vol.api.domain.paciente;

import med.vol.api.domain.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository ;

    public Page<DadosListagemPaciente> listar(Pageable pageable){
        try{
            Page<DadosListagemPaciente> page = pacienteRepository.findAllByAtivoTrue(pageable).map(paciente -> new DadosListagemPaciente(paciente));
            return page;
        }catch (Exception e){
            throw new ValidacaoException("Algo ocorreu na listagem");
        }
    }

    public DadosDetalhamentoPaciente atualizar(DadosAtualizacaoPaciente dados) {
        try{
            Paciente paciente  = pacienteRepository.getReferenceById(dados.id());
            paciente.atualizarInformacoes(dados);
            return new DadosDetalhamentoPaciente(paciente);
        }catch (Exception e){
            throw new ValidacaoException("Algo ocorreu na atualização");
        }
    }

    public DadosDetalhamentoPaciente detalhar(Long id) {
        try {
           Paciente paciente = pacienteRepository.getReferenceById(id);
           return new DadosDetalhamentoPaciente(paciente);

        }catch (Exception e){
            throw new ValidacaoException("Algo ocorreu na hora de buscar no banco");
        }
    }
}
