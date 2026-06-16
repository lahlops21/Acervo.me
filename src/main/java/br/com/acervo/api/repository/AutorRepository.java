package br.com.acervo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acervo.api.model.autor.Autor;

public interface AutorRepository extends JpaRepository<Autor, Integer> {
  
}
