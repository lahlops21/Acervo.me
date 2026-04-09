package br.com.acervo.api.model.emprestimo;

import java.time.LocalDateTime;

import br.com.acervo.api.model.administrador.Administrador;
import br.com.acervo.api.model.exemplar.Exemplar;
import br.com.acervo.api.model.usuario.Usuario;

public record DadosCadastroEmprestimo(

 Integer id,
 Usuario id_usuario,
 Exemplar id_exemplar,
 Administrador id_administrador,
 LocalDateTime data_emprestimo, 
 LocalDateTime data_prevista, 
 LocalDateTime data_real
  

) {
  
}
