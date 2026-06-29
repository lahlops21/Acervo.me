package br.com.acervo.api.model.livro;

import java.util.List;

import br.com.acervo.api.model.autor.DadosAutorInput;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoLivro(
    @NotNull
    Integer id,
    String titulo,
    String editora,
    String anoPublicacao,
    String sinopse,
    byte[] urlCapa,
    List<DadosAutorInput> autores,   // 👈 Adicionado para atualizar o Autor
    List<String> categorias          // 👈 Adicionado para receber as categorias do fron
) {
  
}
