package med.vol.api.domain.medico;

import med.vol.api.controller.AutorizacaoListar;
import med.vol.api.domain.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;
    public Page<DadosListagemMedicos> listar(AutorizacaoListar autorizacaoListar, Pageable pageable){
        try{
            return medicoRepository.findByLista(autorizacaoListar, pageable).map(m -> new DadosListagemMedicos(m));
        }catch (Exception e){
            throw new ValidacaoException("Algo ocorreu na listagem");
        }
    }

    public DadosDetalhamentoMedico atualizar(DadosAtualizacaoMedico dados) {
        try{
            Medico medico = medicoRepository.getReferenceById(dados.id());
            medico.atualizarInformacoes(dados);
            return new DadosDetalhamentoMedico(medico);
        }catch (Exception e){
            throw new ValidacaoException("Algo ocorreu na atualização");
        }
    }

    public DadosDetalhamentoMedico detalhar(Long id) {
        try{
            Medico medico = medicoRepository.getReferenceById(id);
            return new DadosDetalhamentoMedico(medico);
        }catch (Exception e){
            throw new ValidacaoException("Algo ocorreu na consulta do banco");
        }
    }
    public void excluir(Long id) {
        try{
            Medico medico = medicoRepository.getReferenceById(id);
            medico.excluir();
        }catch (Exception e){
            throw new ValidacaoException("Algo ocorreu na atualização");
        }
    }

    public ResponseEntity cadastrar(DadosCadastroMedico dados, UriComponentsBuilder uriComponentsBuilder) {
        try{
            Medico medico = new Medico(dados);
            medicoRepository.save(medico);
            System.out.println(dados);

            URI uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

            return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
        }catch (Exception e){
            throw new ValidacaoException("Erro ao cadastrar no banco de dados");
        }
    }
}
