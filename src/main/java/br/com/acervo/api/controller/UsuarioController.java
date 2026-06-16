package br.com.acervo.api.controller;

import br.com.acervo.api.model.usuario.Usuario;
import br.com.acervo.api.model.usuario.DadosCadastroUsuario;
import br.com.acervo.api.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroUsuario dados) {
        var usuario = new Usuario(dados);
        repository.save(usuario);
        return ResponseEntity.ok("Usuário cadastrado com sucesso! ID: " + usuario.getId());
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        var lista = repository.findAll();
        return ResponseEntity.ok(lista);
    }
}