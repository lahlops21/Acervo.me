package br.com.acervo.api.repository;

import br.com.acervo.api.model.livro.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    // JpaRepository já nos dá o método findAll() de graça!
}
