package br.com.acervo.api.model.usuario;


public record DadosCadastroUsuario(

   String nome,
    String email,
    String cpf,
    String senhaRaw,
    String telefone,
    String endereco

) {
  
}
