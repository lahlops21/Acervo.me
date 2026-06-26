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
}
