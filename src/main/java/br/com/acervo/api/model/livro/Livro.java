package br.com.acervo.api.model.livro;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "livros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Livro {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String isbn;
  private String titulo;
  private String editora;
  private LocalDateTime ano_publicacao;
  @Column(columnDefinition = "TEXT")
  private String sinopse;


  public Livro(DadosCadastroLivro dados){

    this.isbn = dados.isbn();
    this.titulo = dados.titulo();
    this.editora = dados.editora();
    this.ano_publicacao = dados.ano_publicacao();
    this.sinopse = dados.sinopse();

  }

}
