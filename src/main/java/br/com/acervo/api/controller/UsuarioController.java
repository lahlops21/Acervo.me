package br.com.acervo.api.controller;

import br.com.acervo.api.model.usuario.Usuario;
import br.com.acervo.api.model.usuario.DadosCadastroUsuario;
import br.com.acervo.api.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroUsuario dados) {
        //  Passa o DTO 'dados' e extrai a senha dele como segundo parâmetro
        String senhaCriptografada = passwordEncoder.encode(dados.senhaRaw());
        var usuario = new Usuario(dados, senhaCriptografada);
        repository.save(usuario);
       // Retorna o código gerado em vez do ID 
    return ResponseEntity.ok("Usuário cadastrado com sucesso! Código do Leitor: " + usuario.getCodigo());
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        var lista = repository.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/pesquisa")
public ResponseEntity<?> buscarPorCodigo(@RequestParam String codigo) {
    // Busca na tabela Usuario pelo código gerado automaticamente
    var usuario = repository.findByCodigo(codigo);
    if (usuario.isPresent()) {
        return ResponseEntity.ok(usuario.get());
    }
    return ResponseEntity.notFound().build();
}

}