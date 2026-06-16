package br.com.acervo.api.controller;

import br.com.acervo.api.model.exemplar.DadosCadastroNovoExemplar;
import br.com.acervo.api.model.exemplar.Exemplar;
import br.com.acervo.api.service.ExemplarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("exemplares")
public class ExemplarController {

    @Autowired
    private ExemplarService service; // Chama o "cérebro" do fluxo

    @PostMapping
    public ResponseEntity<String> cadastrarExemplar(@RequestBody @Valid DadosCadastroNovoExemplar dados) {
        Exemplar novoExemplar = service.cadastrarNovoExemplar(dados);
        return ResponseEntity.ok("Exemplar cadastrado com sucesso! Tombo: " + novoExemplar.getTombo() + 
                                 " | Vinculado ao Livro ID: " + novoExemplar.getLivro().getId());
    }
}