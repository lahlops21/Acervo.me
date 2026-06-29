package br.com.acervo.api.model.exemplar;

import java.util.List;

import br.com.acervo.api.model.livro.Livro;

public record DadosDetalhamentoCompletoLivro(
    Integer idLivro,
    String isbn,
    String titulo,
    List<String> nomesAutores, // ALTERADO
    String editora,
    String anoPublicacao,
    String sinopse,
    byte[] urlCapa,
    Integer quantidadeExemplares,
    String statusDisponibilidade,
    List<String> categorias
) {
  // Dentro de DadosDetalhamentoCompletoLivro.java

// CONSTRUTOR INTELIGENTE ATUALIZADO:
public DadosDetalhamentoCompletoLivro(Livro livro, Exemplar exemplar, List<String> listaDeAutores) { // MUDADO AQUI
    this(
        livro.getId(),
        livro.getIsbn(), 
        livro.getTitulo(),
        listaDeAutores, // MUDADO AQUI (Agora o Java sabe que é o parâmetro!)
        livro.getEditora(),
        livro.getAnoPublicacao(),
        livro.getSinopse(),
        livro.getUrlCapa(),
        livro.getQuantidadeExemplares(),
        exemplar.getStatus().toString(),
        livro.getCategorias()
    );
}

}
