package br.com.acervo.api.model.exemplar;

import java.util.List;
import br.com.acervo.api.model.livro.Livro;
import br.com.acervo.api.model.livro.Categoria; // Importa a nova classe Categoria

public record DadosDetalhamentoCompletoLivro(
    Integer idLivro,
    String isbn,
    String titulo,
    List<String> nomesAutores, 
    String editora,
    String anoPublicacao,
    String sinopse,
    byte[] urlCapa,
    Integer quantidadeExemplares,
    String statusDisponibilidade,
    List<String> categorias // 🚀 Voltou para String para mandar os nomes limpos ("FANTASIA") ao Front-end
) {

    // CONSTRUTOR INTELIGENTE ATUALIZADO E SEGURO:
    public DadosDetalhamentoCompletoLivro(Livro livro, Exemplar exemplar, List<String> listaDeAutores) { 
        this(
            livro.getId(),
            livro.getIsbn(), 
            livro.getTitulo(),
            listaDeAutores, 
            livro.getEditora(),
            livro.getAnoPublicacao(),
            livro.getSinopse(),
            livro.getUrlCapa(),
            livro.getQuantidadeExemplares(),
            
            
            exemplar != null ? exemplar.getStatus().toString() : "INDISPONIVEL",
            // Transforma a List<Categoria> em List<String> pegando apenas os nomes
            livro.getCategorias() != null ? 
                livro.getCategorias().stream().map(Categoria::getNome).toList() : 
                java.util.Collections.emptyList()
        );
    }
}