package br.com.acervo.api.model.livro;

import java.time.LocalDateTime;

public record DadosCadastroLivro(

   Integer id,
   String isbn,
   String titulo,
   String editora,
   LocalDateTime ano_publicacao,
   String sinopse

) {
  
}
