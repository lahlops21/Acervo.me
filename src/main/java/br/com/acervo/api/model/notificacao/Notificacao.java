package br.com.acervo.api.model.notificacao;

import br.com.acervo.api.model.emprestimo.Emprestimo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notificacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Notificacao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Emprestimo id_emprestimo;
  private TipoNotificacao tipo; 
  private boolean sucesso; 
  
}