package br.com.acervo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.acervo.api.repository.ExemplarRepository;
import br.com.acervo.api.model.exemplar.DadosDetalhamentoCompletoLivro;
import br.com.acervo.api.model.livro.DadosAtualizacaoLivro;
import br.com.acervo.api.repository.LivroRepository;
import br.com.acervo.api.service.LivroService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("livros")
public class LivroController {

@Autowired
private LivroRepository livroRepository;

@Autowired
private ExemplarRepository exemplarRepository;

@PutMapping
@Transactional
public ResponseEntity<?> atualizar(@RequestBody @Valid DadosAtualizacaoLivro dados) {
    // 1. Busca o registro existente
    var livro = livroRepository.findById(dados.id())
            .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));
    
    // 2. Modifica os campos com base no que veio no DTO
    livro.atualizarInformacoes(dados);
    
    // 3. Retorna o objeto atualizado e o status 200 OK
    return ResponseEntity.ok(livro);
}


    @DeleteMapping("/{id}")
    @Transactional // 🔴 ESSA ANOTAÇÃO É OBRIGATÓRIA AQUI!
    public ResponseEntity<?> excluir(@PathVariable Integer id) {
        var livro = livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

        // 🌟 A MÁGICA DA EXCLUSÃO DEFINITIVA:
        // Primeiro, vamos no banco e deletamos todos os exemplares que pertencem a esse livro
        exemplarRepository.deleteByLivro(livro); 

        // Agora que a tabela Exemplar está limpa para esse ID, deletamos o livro sem nenhum erro!
        livroRepository.delete(livro);

        return ResponseEntity.ok("Livro e todos os seus exemplares associados foram excluídos definitivamente!");
    }

    @Autowired
    private LivroService livroService; // Injeta o Service em vez do Repository diretamente!

    @GetMapping("/{id}") // Rota para detalhar um livro específico pelo ID
    public ResponseEntity<DadosDetalhamentoCompletoLivro> detalharLivroCompleto(@PathVariable Integer id) {
        // Delega a busca e a montagem das 3 camadas para a camada Service
        var dadosDetalhados = livroService.obterDetalhamentoDoLivro(id);
        
        return ResponseEntity.ok(dadosDetalhados);
    }

    @GetMapping("/pesquisa")
public ResponseEntity<List<DadosDetalhamentoCompletoLivro>> buscarPorAutor(@RequestParam String autor) {
    // Exemplo de URL: /livros/pesquisa?autor=Clarice
    var livros = livroService.pesquisarLivrosPorAutor(autor);
    return ResponseEntity.ok(livros);
}

}

    

