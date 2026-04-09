package br.com.acervo.api.model.usuario;

import java.time.LocalDateTime;

public record DadosCadastroUsuario(

   Integer id,
   String codigo,
   String nome,
   String email,
   String telefone,
   String endereco,
   LocalDateTime criadoEm

) {
  
}
