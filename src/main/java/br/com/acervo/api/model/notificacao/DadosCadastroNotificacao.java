package br.com.acervo.api.model.notificacao;

import br.com.acervo.api.model.emprestimo.Emprestimo;

public record DadosCadastroNotificacao(

 Integer id,
 Emprestimo id_emprestimo,
 TipoNotificacao tipo, 
 boolean sucesso

) {
  
}
