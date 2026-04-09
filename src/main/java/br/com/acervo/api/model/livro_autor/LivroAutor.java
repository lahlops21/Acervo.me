package br.com.acervo.api.model.livro_autor;

import br.com.acervo.api.model.autor.Autor;
import br.com.acervo.api.model.livro.Livro;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "emprestimos") 
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class LivroAutor {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Livro id_livro; 
  private Autor id_autor; 
}
