package br.com.acervo.api.model.livro;


import java.util.ArrayList;
import java.util.List;

import br.com.acervo.api.model.autor.Autor;
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

  // 👈 CORREÇÃO: Alinhado com a tabela intermediária 'Livro_Categoria' do seu script SQL
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "Livro_Categoria", joinColumns = @JoinColumn(name = "id_livro"))
  @Column(name = "id_categoria") 
  private List<String> categorias = new ArrayList<>();

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "Livro_Autor", // Nome da tabela intermediária que o H2 vai criar
      joinColumns = @JoinColumn(name = "id_livro"), // Coluna que aponta para o Livro
      inverseJoinColumns = @JoinColumn(name = "id_autor") // Coluna que aponta para o Autor
  )
  private List<Autor> autores = new ArrayList<>();

  @Column(name = "editora")
  private String editora;

  @Column(name = "ano_publicacao")
  private String anoPublicacao;

  
  @Column(name = "sinopse", columnDefinition = "TEXT")
  private String sinopse;

  @Lob
  @Column(name = "url_capa", columnDefinition = "BLOB") 
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

  
  public void atualizarInformacoes(DadosAtualizacaoLivro dados, List<Autor> novosAutores) {
    if (dados.titulo() != null) this.titulo = dados.titulo();
    if (dados.editora() != null) this.editora = dados.editora();
    if (dados.anoPublicacao() != null) this.anoPublicacao = dados.anoPublicacao();
    if (dados.sinopse() != null) this.sinopse = dados.sinopse();
    if (dados.urlCapa() != null) this.urlCapa = dados.urlCapa();
// Se o front enviar autores na edição, substitui a lista antiga
    if (novosAutores != null) {
        this.autores.clear();
        this.autores.addAll(novosAutores);
    }

    // Se o front enviar categorias na edição, substitui as antigas
    if (dados.categorias() != null) {
        this.categorias.clear();
        this.categorias.addAll(dados.categorias());
    }
}

}