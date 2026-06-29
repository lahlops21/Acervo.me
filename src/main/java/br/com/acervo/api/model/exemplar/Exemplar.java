package br.com.acervo.api.model.exemplar;

import br.com.acervo.api.model.livro.Livro;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Exemplar")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Exemplar {

  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_exemplar")
  private Integer id;
  
  @ManyToOne
  @JoinColumn(name = "id_livro", nullable = false)
  private Livro livro;
  
  @Column(name = "numero_tombo", nullable = false, unique = true)
  private String tombo;
  
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private StatusLivro status = StatusLivro.DISPONIVEL;

  // Construtor para criar a cópia física a partir do DTO unificado
public Exemplar(Livro livroAtribuido, String tomboGerado) {
    this.status = StatusLivro.DISPONIVEL; // Todo livro novo chega disponível
    this.livro = livroAtribuido; // Vincula ao livro (seja o criado agora ou o que já existia)
    this.tombo = tomboGerado; // 👈 Evita erro de valor nulo no MySQL
}
}
