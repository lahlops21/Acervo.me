package br.com.acervo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.acervo.api.repository.AutorRepository;
import br.com.acervo.api.repository.ExemplarRepository;
import br.com.acervo.api.model.autor.Autor;
import br.com.acervo.api.model.exemplar.DadosDetalhamentoCompletoLivro;
import br.com.acervo.api.model.livro.DadosAtualizacaoLivro;
import br.com.acervo.api.repository.LivroRepository;
import br.com.acervo.api.service.LivroService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("livros")
@CrossOrigin(origins = "*")
public class LivroController {

@Autowired
private LivroRepository livroRepository;

@Autowired
private ExemplarRepository exemplarRepository;

@Autowired
private AutorRepository autorRepository;

@PutMapping
@Transactional
public ResponseEntity<?> atualizar(@RequestBody @Valid DadosAtualizacaoLivro dados) {
    var livro = livroRepository.findById(dados.id())
            .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));
    
    List<Autor> listaAutoresAtualizada = null;

    // 👈 Aplica sua lógica peculiar: busca ou cria o autor dinamicamente na edição
    if (dados.autores() != null && !dados.autores().isEmpty()) {
        listaAutoresAtualizada = new java.util.ArrayList<>();
        for (var autorInput : dados.autores()) {
            if (autorInput.nome() != null && !autorInput.nome().trim().isEmpty()) {
                // Tenta achar pelo nome ignorando espaços extras
                String nomeFormatado = autorInput.nome().trim();
                Autor autor = autorRepository.findByNome(nomeFormatado) // Nota: Certifique-se de que o AutorRepository está injetado como 'repository' ou altere o nome aqui
                        .orElseGet(() -> {
                            Autor novo = new Autor();
                            novo.setNome(nomeFormatado);
                            return autorRepository.save(novo); // Cria e salva dinamicamente se for um autor inédito
                        });
                listaAutoresAtualizada.add(autor);
            }
        }
    }
    
    // Modifica os campos repassando a lista de autores tratada
    livro.atualizarInformacoes(dados, listaAutoresAtualizada);
    
    return ResponseEntity.ok(livro);
}


    @DeleteMapping("/{id}")
    @Transactional 
    public ResponseEntity<?> excluir(@PathVariable Integer id) {
        var livro = livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

        // EXCLUSÃO DEFINITIVA:
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
public ResponseEntity<List<DadosDetalhamentoCompletoLivro>> buscarLivros(@RequestParam String termo) {
    // Agora aceita qualquer texto! Ex: /livros/pesquisa?termo=978-85 ou /livros/pesquisa?termo=Cortic%C3%A7o
    var livros = livroService.pesquisarLivrosGeral(termo);
    return ResponseEntity.ok(livros);
}

@GetMapping 
public ResponseEntity<List<DadosDetalhamentoCompletoLivro>> listar() {
    // Aciona o service para trazer todos os cards de livros formatados
    var lista = livroService.listarTodosOsLivros();
    return ResponseEntity.ok(lista);
}
}

    

