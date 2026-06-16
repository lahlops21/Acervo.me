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

@OneToOne // <-- Adicione esta anotação se quiser mapear a relação real
@JoinColumn(name = "id_emprestimo") // Nome correto da coluna FK no banco
private Emprestimo emprestimo; // Mude o tipo/nome para o padrão de objeto

@Enumerated(EnumType.STRING) // Garante que o Enum grave o texto no banco
private TipoNotificacao tipo; 

private boolean sucesso; 
  
}