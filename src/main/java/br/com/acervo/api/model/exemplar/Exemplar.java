package br.com.acervo.api.model.exemplar;

import br.com.acervo.api.model.livro.Livro;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exemplares")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Exemplar {

  @Id 
   @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Livro id_livro;
  private String tombo;
  private StatusLivro status;

}
