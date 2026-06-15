package br.com.acervo.api.model.autor;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "Autor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Autor {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_autor")
  private Integer id;
  @Column(name = "nome")
  private String nome;

  public Autor(DadosCadastroAutor dados) {
  this.nome = dados.nome();
}
}
