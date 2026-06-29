package br.com.acervo.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.acervo.api.model.livro.Livro;
import br.com.acervo.api.model.exemplar.DadosDetalhamentoCompletoLivro;
import br.com.acervo.api.repository.LivroRepository;
import br.com.acervo.api.repository.ExemplarRepository;
import br.com.acervo.api.repository.AutorRepository;

@Service // Informa ao Spring que esta classe guarda a lógica de negócio
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private ExemplarRepository exemplarRepository;

    @Autowired
    private AutorRepository autorRepository;

    public DadosDetalhamentoCompletoLivro obterDetalhamentoDoLivro(Integer idLivro) {
        // 1. Busca as informações bases do Livro
        Livro livro = livroRepository.findById(idLivro)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

        // 2. Busca o primeiro exemplar disponível para expor a tag de status na tela
        var exemplar = exemplarRepository.findAll().stream()
                .filter(e -> e.getLivro().getId().equals(idLivro))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nenhum exemplar físico encontrado para este livro"));

        // 3. Busca a lista com os nomes de todos os autores vinculados ao livro
        java.util.List<String> listaDeNomes = livro.getAutores().stream()
                .map(br.com.acervo.api.model.autor.Autor::getNome)
                .toList();

        // Se o livro não tiver nenhum autor cadastrado na tabela pivô, coloca um aviso
        if (listaDeNomes.isEmpty()) {
            listaDeNomes = java.util.Collections.singletonList("Autor Desconhecido");
        }

        // 4. Junta tudo e monta o DTO combinado passando a lista correta
        return new DadosDetalhamentoCompletoLivro(livro, exemplar, listaDeNomes);
    }

    public List<DadosDetalhamentoCompletoLivro> pesquisarLivrosPorAutor(String nomeAutor) {
    // 1. Busca os livros no repositório pelo nome do autor
    List<Livro> livrosEncontrados = livroRepository.findByAutoresNomeContainingIgnoreCase(nomeAutor);
    
    // 2. Transforma cada livro encontrado no DTO de detalhamento combinado
    return livrosEncontrados.stream()
            .map(livro -> {
                // Busca o primeiro exemplar desse livro para ver o status
                var exemplar = exemplarRepository.findAll().stream()
                        .filter(e -> e.getLivro().getId().equals(livro.getId()))
                        .findFirst()
                        .orElse(null); // Caso não tenha exemplar físico ainda
                
                // Mapeia os nomes dos autores desse livro para uma lista de Strings
                List<String> nomes = livro.getAutores().stream()
                        .map(br.com.acervo.api.model.autor.Autor::getNome)
                        .toList();
                
                return new DadosDetalhamentoCompletoLivro(livro, exemplar, nomes);
            })
            .toList();
}

public List<DadosDetalhamentoCompletoLivro> pesquisarLivrosGeral(String termoBusca) {
    java.util.List<Livro> livrosEncontrados = new java.util.ArrayList<>();

    if (termoBusca != null) {
        // 🚀 MÁGICA: Remove todos os traços e pontos caso o termo seja um ISBN formatado
        String termoLimpo = termoBusca.replaceAll("[^0-9a-zA-Z]", "");

        // Tenta buscar primeiro com o termo limpo
        var livroPorIsbn = livroRepository.findByIsbn(termoLimpo);
        if (livroPorIsbn.isPresent()) {
            livrosEncontrados.add(livroPorIsbn.get());
        } else {
            // Fallback: tenta buscar com o termo original (caso tenha letras ou seja título)
            var livroPorIsbnOriginal = livroRepository.findByIsbn(termoBusca);
            if (livroPorIsbnOriginal.isPresent()) {
                livrosEncontrados.add(livroPorIsbnOriginal.get());
            } else {
                // Se não for ISBN de nenhum dos jeitos, segue para busca por título e autor
                var porTitulo = livroRepository.findByTituloContainingIgnoreCase(termoBusca);
                var porAutor = livroRepository.findByAutoresNomeContainingIgnoreCase(termoBusca);
                
                livrosEncontrados.addAll(porTitulo);
                porAutor.forEach(livro -> {
                    if (!livrosEncontrados.contains(livro)) {
                        livrosEncontrados.add(livro);
                    }
                });
            }
        }
    }

    // 4. Converte a lista final de Livros para o DTO que o front-end espera receber
    return livrosEncontrados.stream()
            .map(livro -> {
                var exemplar = exemplarRepository.findAll().stream()
                        .filter(e -> e.getLivro().getId().equals(livro.getId()))
                        .findFirst()
                        .orElse(null);
                
                List<String> nomes = livro.getAutores().stream()
                        .map(br.com.acervo.api.model.autor.Autor::getNome)
                        .toList();
                
                return new DadosDetalhamentoCompletoLivro(livro, exemplar, nomes);
            })
            .toList();
}

public List<DadosDetalhamentoCompletoLivro> listarTodosOsLivros() {
    // 1. Busca todos os livros cadastrados na base de dados
    List<Livro> todosLivros = livroRepository.findAll();
    
    // 2. Transforma cada livro no DTO combinado de detalhamento
    return todosLivros.stream()
            .map(livro -> {
                // Busca o primeiro exemplar desse livro para mapear o status
                var exemplar = exemplarRepository.findAll().stream()
                        .filter(e -> e.getLivro().getId().equals(livro.getId()))
                        .findFirst()
                        .orElse(null);
                
                // Mapeia os nomes dos autores
                List<String> nomes = livro.getAutores().stream()
                        .map(br.com.acervo.api.model.autor.Autor::getNome)
                        .toList();
                
                return new DadosDetalhamentoCompletoLivro(livro, exemplar, nomes);
            })
            .toList();
        
        }
}
