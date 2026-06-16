package br.com.acervo.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.acervo.api.model.livro.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer>{
  // O Spring Boot vai criar a query SQL por trás dos panos magicamente só por causa do nome do método!
    Optional<Livro> findByIsbn(String isbn);
}
