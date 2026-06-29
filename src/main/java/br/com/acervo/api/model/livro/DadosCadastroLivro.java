package br.com.acervo.api.model.livro;

import java.util.List;

public record DadosCadastroLivro(

   Integer id,
   String isbn,
   String titulo,
   String editora,
   String anoPublicacao,
   String sinopse,
   List<Integer> categorias,
   byte[] urlCapa

) {
  
}
