package br.com.acervo.api.model.livro;



public record DadosCadastroLivro(

   Integer id,
   String isbn,
   String titulo,
   String editora,
   String anoPublicacao,
   String sinopse,
   byte[] urlCapa

) {
  
}
