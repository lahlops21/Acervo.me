package br.com.acervo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.acervo.api.model.exemplar.Exemplar;

public interface ExemplarRepository extends JpaRepository<Exemplar, Integer>{
  
}
