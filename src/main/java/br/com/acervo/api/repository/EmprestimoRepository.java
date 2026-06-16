package br.com.acervo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.acervo.api.model.emprestimo.Emprestimo;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer> {
  
}
