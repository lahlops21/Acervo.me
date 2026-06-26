package br.com.acervo.api.model.livro;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoLivro(
    @NotNull
    Integer id,
    String titulo,
    String editora,
    String anoPublicacao,
    String sinopse,
     byte[] urlCapa
) {
  
}
