package br.com.acervo.api.model.exemplar;

import java.util.List;

import br.com.acervo.api.model.autor.DadosAutorInput;


public record DadosCadastroNovoExemplar(
    String tombo,
    String isbn,
    String titulo,
    String editora,
    String anoPublicacao,
    String sinopse,
    byte[] urlCapa,
    List<Integer> categorias, 
    List<DadosAutorInput> autores 
) {}