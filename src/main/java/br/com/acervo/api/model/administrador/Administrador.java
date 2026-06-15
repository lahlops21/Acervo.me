package br.com.acervo.api.model.administrador;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "Administrador")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Administrador {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_admin")
  private Integer id;
  private String nome;
  private String email;
  @Column(name = "senha_hash")
  private String senhaHash;

  public Administrador(DadosCadastroAdministrador dados) {
    this.nome = dados.nome();
    this.email = dados.email();
    // Usar um BCrypt para transformar a senha pura em Hash
    this.senhaHash = dados.senhaRaw(); 
}
}
