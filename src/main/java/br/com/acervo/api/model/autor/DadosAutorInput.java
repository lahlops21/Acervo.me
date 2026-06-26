package br.com.acervo.api.model.autor;

public record DadosAutorInput(
    Integer id, // Preenchido se o autor já existir
    String nome // Preenchido obrigatóriamente se for um autor novo
) {
  
}
