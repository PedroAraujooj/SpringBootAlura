package med.vol.api.domain.paciente;

import med.vol.api.domain.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository ;

    public Page<DadosListagemPaciente> listar(Pageable pageable){
        try{
            Page<DadosListagemPaciente> page = pacienteRepository.findAllByAtivoTrue(pageable).map(paciente -> new DadosListagemPaciente(paciente));
            return page;
        }catch (Exception e){
            throw new ValidacaoException("Algo ocorreu na listagem do banco de dados ");
        }
    }

    public DadosDetalhamentoPaciente atualizar(DadosAtualizacaoPaciente dados) {
        try{
            Paciente paciente  = pacienteRepository.getReferenceById(dados.id());
            paciente.atualizarInformacoes(dados);
            return new DadosDetalhamentoPaciente(paciente);
        }catch (Exception e){
            throw new ValidacaoException("Algo ocorreu na atualização do banco de dados ");
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

    public void excluir(Long id) {
        try{
            var paciente = pacienteRepository.getReferenceById(id);
            paciente.excluir();
        }catch (Exception e){
            throw new ValidacaoException("Algo deu errado na hora de excluir no bando de dados");
        }

    }

    public ResponseEntity cadastrar(DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
        try{
            var paciente = new Paciente(dados);
            pacienteRepository.save(paciente);

            var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
            return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
        }catch (Exception e){
            throw new ValidacaoException("Erro ao cadastar no banco de dados");
        }
    }
}
