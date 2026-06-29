package br.com.acervo.api.model.livro;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categoria")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer id;

    @Column(name = "nome") 
    private String nome;


    // Esse atributo NÃO cria uma coluna na tabela 'categoria'.
    // Ele apenas diz ao Hibernate para olhar a tabela intermediária 'Livro_Categoria'
    // e carregar quais livros usam essa categoria de forma automatizada!
    @ManyToMany(mappedBy = "categorias")
    @JsonIgnoreProperties({"categorias", "autores", "urlCapa", "sinopse"}) 
    private List<Livro> livros = new ArrayList<>();
}
