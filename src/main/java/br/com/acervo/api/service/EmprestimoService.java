package br.com.acervo.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.acervo.api.model.emprestimo.*;
import br.com.acervo.api.model.exemplar.StatusLivro;
import br.com.acervo.api.model.usuario.UsuarioStatus;
import br.com.acervo.api.repository.*;
import jakarta.transaction.Transactional;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; // Substitua pelo nome correto do seu Repository de Usuario

    @Autowired
    private ExemplarRepository examplarRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Transactional
public Emprestimo abrirEmprestimo(DadosCadastroEmprestimo dados) {
    var usuario = usuarioRepository.findById(dados.idUsuario())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
            
    var exemplar = examplarRepository.findById(dados.idExemplar())
            .orElseThrow(() -> new IllegalArgumentException("Exemplar não encontrado"));
            
    var administrador = administradorRepository.findById(dados.idAdmin())
            .orElseThrow(() -> new IllegalArgumentException("Administrador não encontrado"));
    
    var livro = exemplar.getLivro(); // 👈 Pega o livro pai desse exemplar

    // Validações que você já criou
    if (usuario.getStatus() == UsuarioStatus.INDISPONIVEL) {
        throw new IllegalStateException("Empréstimo negado! O leitor já possui um empréstimo ativo.");
    }

    // Usa o seu Enum StatusLivro que controla a cópia física
    if (exemplar.getStatus() == StatusLivro.EMPRESTADO) {
        throw new IllegalStateException("Empréstimo negado! Este exemplar físico já está alugado.");
    }

    // 🔄 REGRAS DE ESTOQUE E STATUS NA ABERTURA:
    usuario.setStatus(UsuarioStatus.INDISPONIVEL);
    exemplar.setStatus(StatusLivro.EMPRESTADO); // 👈 Muda o status da cópia física
    
    // 📉 Diminui a quantidade de exemplares disponíveis da obra geral no catálogo
    if (livro.getQuantidadeExemplares() > 0) {
        livro.setQuantidadeExemplares(livro.getQuantidadeExemplares() - 1);
    }

    Emprestimo emprestimo = new Emprestimo();
    // ... preenche os dados do empréstimo normais ...
    emprestimo.setUsuario(usuario);
    emprestimo.setExemplar(exemplar);
    emprestimo.setAdministrador(administrador);
    emprestimo.setDataEmprestimo(LocalDateTime.now());
    emprestimo.setDataDevolucaoPrevista(LocalDate.now().plusDays(7));
    emprestimo.setStatus(StatusEmprestimo.ATIVO);

    return emprestimoRepository.save(emprestimo);
}

    

    public List<DadosDetalhamentoEmprestimo> listarTodosOsEmprestimos() {
    // 1. Busca absolutamente todos os empréstimos gravados no banco H2
    List<Emprestimo> todosEmprestimos = emprestimoRepository.findAll();
    
    // 2. Transforma a lista de Entidades em uma lista de DTOs de saída seguros
    return todosEmprestimos.stream()
            .map(DadosDetalhamentoEmprestimo::new)
            .toList();
}

@Transactional
public Emprestimo devolverEmprestimo(Integer idEmprestimo) {
    var emprestimo = emprestimoRepository.findById(idEmprestimo)
            .orElseThrow(() -> new IllegalArgumentException("Ficha de empréstimo não localizada."));

    if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
        throw new IllegalStateException("Este empréstimo já foi encerrado.");
    }

    var usuario = emprestimo.getUsuario();
    var exemplar = emprestimo.getExemplar();
    var livro = exemplar.getLivro(); // 👈 Pega o livro pai desse exemplar

    // 🔄 REGRAS DE ESTOQUE E STATUS NA DEVOLUÇÃO:
    usuario.setStatus(UsuarioStatus.DISPONIVEL);
    exemplar.setStatus(StatusLivro.DISPONIVEL); // 👈 Libera a cópia física para o próximo leitor

    // 📈 Aumenta a quantidade de exemplares do livro pai no estoque geral
    livro.setQuantidadeExemplares(livro.getQuantidadeExemplares() + 1);

    emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
    emprestimo.setDataDevolucaoReal(LocalDate.now());

    return emprestimoRepository.save(emprestimo);
}


}