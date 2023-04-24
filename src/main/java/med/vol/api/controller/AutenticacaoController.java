package med.vol.api.controller;

import jakarta.validation.Valid;
import med.vol.api.domain.usuario.Usuario;
import med.vol.api.infra.security.DadosTokenJWT;
import med.vol.api.infra.security.TokenService;
import med.vol.api.domain.usuario.DadosAutenticacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dadosAutenticacao){
        System.out.println("@# ENTREI AQUI SUPERMAN !");
        var token = new UsernamePasswordAuthenticationToken(dadosAutenticacao.login(), dadosAutenticacao.senha());
        Authentication authentication = manager.authenticate((Authentication) token);

        String tokenReturn = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenReturn));
    }
}
