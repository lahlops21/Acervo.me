package br.com.acervo.api.model.livro;


import br.com.acervo.api.model.exemplar.DadosCadastroNovoExemplar;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Livro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Livro {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_livro")
  private Integer id;
  
  @Column(name = "isbn")
  private String isbn;

  @Column(name = "titulo")
  private String titulo;

  @Column(name = "editora")
  private String editora;

  @Column(name = "ano_publicacao")
  private String anoPublicacao;

  
  @Column(name = "sinopse", columnDefinition = "TEXT")
  private String sinopse;

  @Lob
  @Column(name = "url_capa", columnDefinition = "LONGBLOB") // LONGBLOB garante espaço para imagens maiores
  private byte[] urlCapa;
  
@Column(name = "quantidade_exemplares")
private Integer quantidadeExemplares = 0;


  public Livro(DadosCadastroLivro dados){

    this.isbn = dados.isbn();
    this.titulo = dados.titulo();
    this.editora = dados.editora();
    this.anoPublicacao = dados.anoPublicacao();
    this.sinopse = dados.sinopse();
    this.urlCapa = dados.urlCapa();


  }

  

}
