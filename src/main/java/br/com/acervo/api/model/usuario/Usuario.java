package br.com.acervo.api.model.usuario;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_usuario")
  private Integer id;

  @Column(name = "codigo_usuario")
  private String codigo;

  @Column(name = "nome")
  private String nome;

  @Column(name = "email")
  private String email;

  @Column(name = "senha")
  private String senha;

  @Column(name = "cpf") // ADICIONADO: Mapeamento do novo campo
  private String cpf;

  @Column(name = "telefone")
  private String telefone;

  @Column(name = "endereco")
  private String endereco;
  
  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private UsuarioStatus status;


  public Usuario(DadosCadastroUsuario dados) {
    this.nome = dados.nome();
    this.email = dados.email();
    this.cpf = dados.cpf();
    this.senha = dados.senhaRaw(); // Também será criptografada depois
    this.telefone = dados.telefone();
    this.endereco = dados.endereco();
    this.status = status.DISPONIVEL; // Todo usuário nasce ativo/disponível
}
}
