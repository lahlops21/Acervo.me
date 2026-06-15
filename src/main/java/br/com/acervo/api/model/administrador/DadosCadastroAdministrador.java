package br.com.acervo.api.model.administrador;

public record DadosCadastroAdministrador(
    String nome,
    String email,
    String senhaRaw // Senha pura vinda do cliente, que o back-end vai criptografar
) {
  
}

