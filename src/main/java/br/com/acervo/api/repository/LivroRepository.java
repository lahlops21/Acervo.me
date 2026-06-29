package br.com.acervo.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.acervo.api.model.livro.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer>{
  // O Spring Boot vai criar a query SQL só por causa do nome do método
    Optional<Livro> findByIsbn(String isbn);

    List<Livro> findByAutoresNomeContainingIgnoreCase(String nomeAutor);

    List<Livro> findByTituloContainingIgnoreCase(String titulo); // Busca por parte do título
   
}
