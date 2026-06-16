package br.com.acervo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.acervo.api.model.notificacao.Notificacao;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer>  {
  
}
