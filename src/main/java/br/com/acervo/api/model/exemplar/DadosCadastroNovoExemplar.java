package br.com.acervo.api.model.exemplar;

import java.util.List;

import br.com.acervo.api.model.autor.DadosAutorInput;

public record DadosCadastroNovoExemplar(
    // Dados do Exemplar Físico (A Cópia)
    String tombo,

    // Dados do Livro (Caso ele precise ser criado do zero)
    String isbn,
    String titulo,
    String editora,
    String anoPublicacao,
    String sinopse,
    byte[] urlCapa,
    List<DadosAutorInput> autores // ALTERADO
) {
}