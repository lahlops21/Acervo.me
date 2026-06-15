package br.com.acervo.api.model.exemplar;

public record DadosCadastroNovoExemplar(
    // Dados do Exemplar Físico (A Cópia)
    String tombo,

    // Dados do Livro (Caso ele precise ser criado do zero)
    String isbn,
    String titulo,
    String editora,
    String anoPublicacao,
    String sinopse,
    byte[] urlCapa
) {
}