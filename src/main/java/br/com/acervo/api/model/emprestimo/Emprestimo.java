package br.com.acervo.api.model.emprestimo;

import java.time.LocalDate;
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
  @Column(name = "id_emprestimo")
  private Integer id;
  
  // Muitos empréstimos podem ser feitos por UM usuário
  @ManyToOne
  @JoinColumn(name = "id_usuario")
  private Usuario usuario;
  
  // Muitos empréstimos podem referenciar o mesmo exemplar físico (em datas diferentes)
  @ManyToOne
  @JoinColumn(name = "id_exemplar") // Nome da coluna FK no banco
  private Exemplar exemplar;

  // Muitos empréstimos podem ser feitos pelo mesmo administrador
  @ManyToOne
  @JoinColumn(name = "id_admin") // Nome da coluna FK no banco
  private Administrador administrador;
  
  @Column(name = "data_emprestimo")
  private LocalDateTime dataEmprestimo; 
  
  @Column(name = "data_devolucao_prevista")
  private LocalDate dataDevolucaoPrevista; 

  @Column(name = "data_devolucao_real")
  private LocalDate dataDevolucaoReal;
  
  @Enumerated(EnumType.STRING)
  private StatusEmprestimo status;
  
}
