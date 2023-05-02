package med.vol.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.vol.api.domain.consulta.AutorizacaoConsulta;
import med.vol.api.domain.consulta.ConsultaService;
import med.vol.api.domain.consulta.DadosAgendamentoConsulta;
import med.vol.api.domain.consulta.DadosDetalhamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {
    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        System.out.println(dados);
        var dto = consultaService.agendar(dados);
        return ResponseEntity.ok(dto);
    }
    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoConsulta>> listar(AutorizacaoConsulta autorizacaoConsulta, @PageableDefault(size = 10, sort = {"id"}) Pageable pagina){
        return ResponseEntity.ok(consultaService.listar(autorizacaoConsulta,pagina));
    }
}
