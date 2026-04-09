package br.com.acervo.api.model.exemplar;

import br.com.acervo.api.model.livro.Livro;

public record DadosCadastroExemplar(

 Integer id,
 Livro id_livro,
 String tombo,
 StatusLivro status

) {
  



}
