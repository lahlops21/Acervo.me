package br.com.acervo.api.model.autor;

public record DadosDetalhamentoAutor(
    Integer id,
    String nome
) {
    // Construtor que transforma a Entidade cheia do banco no DTO de saída
    public DadosDetalhamentoAutor(Autor autor) {
        this(autor.getId(), autor.getNome());
    }
}