package br.com.acervo.api.model.livro_autor;

import br.com.acervo.api.model.autor.Autor;
import br.com.acervo.api.model.livro.Livro;

public record DadosCadastroLivroAutor(

   Livro id_livro, 
   Autor id_autor

) {
  
}
