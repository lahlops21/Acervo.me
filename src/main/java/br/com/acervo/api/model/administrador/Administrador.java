package br.com.acervo.api.model.administrador;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "Administrador")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Administrador implements UserDetails {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_admin")
  private Integer id;
  private String nome;
  private String email;
  @Column(name = "senha_hash")
  private String senhaHash;

  @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // O administrador ganha a role administrativa para poder gerenciar acervo
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() { return this.senhaHash; }
    @Override
    public String getUsername() { return this.email; }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }


 public Administrador(DadosCadastroAdministrador dados, String senhaBCrypt) {
    this.nome = dados.nome();
    this.email = dados.email();
    this.senhaHash = senhaBCrypt; // 👈 Armazenar sempre o Hash gerado, nunca a senha pura
}
}
