package br.com.acervo.api.model.livro;

import java.util.ArrayList;
import java.util.List;
import br.com.acervo.api.model.autor.Autor;
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

  @ManyToMany
  @JoinTable(
      name = "Livro_Categoria",
      joinColumns = @JoinColumn(name = "id_livro", referencedColumnName = "id_livro"),
      inverseJoinColumns = @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
  )
  private List<Categoria> categorias = new ArrayList<>(); 

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "Livro_Autor", 
      joinColumns = @JoinColumn(name = "id_livro"), 
      inverseJoinColumns = @JoinColumn(name = "id_autor") 
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

  // 🚀 CONSTRUTOR CORRIGIDO: Agora preparado para receber os dados limpos estruturados pelo Service
  public Livro(DadosCadastroLivro dados) {
    this.isbn = dados.isbn();
    this.titulo = dados.titulo();
    this.editora = dados.editora();
    this.anoPublicacao = dados.anoPublicacao();
    this.sinopse = dados.sinopse();
    this.urlCapa = dados.urlCapa();
    this.quantidadeExemplares = 0;
  }

  // 🚀 MÉTODO DE ATUALIZAÇÃO CORRIGIDO: Limpo, sem redundâncias e usando a variável certa (this.categorias)
  public void atualizarInformacoes(DadosAtualizacaoLivro dados, List<Autor> novosAutores, List<Categoria> novasCategorias) {
    if (dados.titulo() != null) this.titulo = dados.titulo();
    if (dados.editora() != null) this.editora = dados.editora();
    if (dados.anoPublicacao() != null) this.anoPublicacao = dados.anoPublicacao();
    if (dados.sinopse() != null) this.sinopse = dados.sinopse();
    if (dados.urlCapa() != null) this.urlCapa = dados.urlCapa();
    
    // Se o service tratou e enviou novos autores na edição, substitui a lista antiga
    if (novosAutores != null) {
        this.autores.clear();
        this.autores.addAll(novosAutores);
    }

    // Se o service tratou e enviou as novas categorias na edição, substitui as antigas
    if (novasCategorias != null) {
        this.categorias.clear();
        this.categorias.addAll(novasCategorias);
    }
  }
}
