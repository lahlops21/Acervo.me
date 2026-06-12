package br.com.acervo.api.model.administrador;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "administradores")
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
}
