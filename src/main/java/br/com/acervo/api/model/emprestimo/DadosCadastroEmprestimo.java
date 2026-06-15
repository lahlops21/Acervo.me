package br.com.acervo.api.model.emprestimo;

public record DadosCadastroEmprestimo(
    Integer idUsuario,
    Integer idExemplar,
    Integer idAdmin
) {
    // Ele só recebe os IDs necessários para abrir o empréstimo no H2.
}
