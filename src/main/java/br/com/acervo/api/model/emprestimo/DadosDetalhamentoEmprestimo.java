package br.com.acervo.api.model.emprestimo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosDetalhamentoEmprestimo(
  Integer idEmprestimo,
    String nomeLeitor,
    String codigoLeitor,
    String tituloLivro,
    String tomboExemplar,
    LocalDateTime dataEmprestimo,
    LocalDate dataDevolucaoPrevista,
    LocalDate dataDevolucaoReal,
    StatusEmprestimo status
) {
    // Esse construtor mapeia a Entidade cheia do banco para o DTO de saída
    public DadosDetalhamentoEmprestimo(Emprestimo emprestimo) {
        this(
            emprestimo.getId(),
            emprestimo.getUsuario().getNome(),
            emprestimo.getUsuario().getCodigo(),
            emprestimo.getExemplar().getLivro().getTitulo(),
            emprestimo.getExemplar().getTombo(),
            emprestimo.getDataEmprestimo(),
            emprestimo.getDataDevolucaoPrevista(),
            emprestimo.getDataDevolucaoReal(),
            emprestimo.getStatus()
        );
    }
}
