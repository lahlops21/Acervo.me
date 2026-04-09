package br.com.acervo.api.model.usuario;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String codigo;
  private String nome;
  private String email;
  private String telefone;
  private String endereco;
  private LocalDateTime criadoEm;
  
  @Enumerated(EnumType.STRING)
  private Status status;

}
