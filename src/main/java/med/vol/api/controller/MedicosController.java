package med.vol.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.vol.api.domain.medico.Medico;
import med.vol.api.domain.medico.MedicoRepository;
import med.vol.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicosController {

    @Autowired
    private MedicoService medicoService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriComponentsBuilder){
        return medicoService.cadastrar(dados, uriComponentsBuilder);
    }
    @GetMapping
    public ResponseEntity<Page<DadosListagemMedicos>> listar(AutorizacaoListar autorizacaoListar, @PageableDefault(size = 10, sort = {"nome","id"}) Pageable pagina){
        return ResponseEntity.ok(medicoService.listar(autorizacaoListar  ,pagina));
    }
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        return ResponseEntity.ok(medicoService.atualizar(dados));

    }
   /* @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        repository.deleteById(id);
    }*/
   @DeleteMapping("/{id}")
   @Transactional
   public ResponseEntity excluir(@PathVariable Long id) {
       medicoService.excluir(id);
       return ResponseEntity.noContent().build();
   }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.detalhar(id));
    }
}
