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
  
  private Integer id;
  private String isbn;
  private String titulo;
  private String editora;
  private LocalDateTime ano_publicacao;
  private String sinopse;

}
