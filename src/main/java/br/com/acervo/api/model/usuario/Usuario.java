package br.com.acervo.api.model.usuario;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
  
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

  @Column(name = "senha_hash")
  private String senhaHash;

  @Column(name = "cpf") 
  private String cpf;

  @Column(name = "telefone")
  private String telefone;

  @Column(name = "endereco")
  private String endereco;
  
  @Enumerated(EnumType.STRING) 
  private UsuarioStatus status = UsuarioStatus.DISPONIVEL;

  // 🌟 GERADOR AUTOMÁTICO AQUI:
    // Pega o ano atual (2026) e junta com os 4 primeiros caracteres de um código aleatório único
    @Transient
    String anoAtual = String.valueOf(java.time.LocalDate.now().getYear());
    @Transient
    String sufixoAleatorio = java.util.UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    
    


 // Construtor para o Cadastro do Front-end
  public Usuario(DadosCadastroUsuario dados, String senhaCriptografada) {
      this.nome = dados.nome();
      this.email = dados.email();
      this.senhaHash = senhaCriptografada; // Receberá a senha protegida por BCrypt
      this.cpf = dados.cpf();
      this.telefone = dados.telefone();
      this.endereco = dados.endereco();
      this.status = UsuarioStatus.DISPONIVEL; // Todo usuário nasce ativo/disponível
      this.codigo = "LEI-" + anoAtual + "-" + sufixoAleatorio; // Resultado: LEI-2026-F83C
}


// 🌟 MÉTODOS OBRIGATÓRIOS DO USERDETAILS QUE RESOLVEM O SEU ERRO:
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
      // Define a role padrão para os leitores do sistema
      return List.of(new SimpleGrantedAuthority("ROLE_LEITOR"));
  }

  @Override
  public String getPassword() {
      return this.senhaHash; // Vincula a senha ao fluxo de autenticação
  }

  @Override
  public String getUsername() {
      return this.email; // Define o e-mail como campo identificador (login)
  }

  @Override
  public boolean isAccountNonExpired() {
      return true;
  }

  @Override
  public boolean isAccountNonLocked() {
      return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
      return true;
  }

  @Override
  public boolean isEnabled() {
      return true; // Deixe o Spring Security dar passagem livre para o usuário autenticado
  }
}

