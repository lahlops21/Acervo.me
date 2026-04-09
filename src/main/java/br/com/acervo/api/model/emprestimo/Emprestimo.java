package br.com.acervo.api.model.emprestimo;

import java.time.LocalDateTime;
import br.com.acervo.api.model.administrador.Administrador;
import br.com.acervo.api.model.exemplar.Exemplar;
import br.com.acervo.api.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "emprestimos") 
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Emprestimo {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Usuario id_usuario;
  private Exemplar id_exemplar;
  private Administrador id_administrador;
  private LocalDateTime data_emprestimo; 
  private LocalDateTime data_prevista; 
  private LocalDateTime data_real; 
  
}
