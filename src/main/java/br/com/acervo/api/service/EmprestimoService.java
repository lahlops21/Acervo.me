package br.com.acervo.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.acervo.api.model.emprestimo.*;
import br.com.acervo.api.repository.*;

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

    public Emprestimo abrirEmprestimo(DadosCadastroEmprestimo dados) {
        // Busca as entidades reais que já existem no banco de dados
        var usuario = usuarioRepository.findById(dados.idUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
                
        var exemplar = examplarRepository.findById(dados.idExemplar())
                .orElseThrow(() -> new IllegalArgumentException("Exemplar não encontrado"));
                
        var administrador = administradorRepository.findById(dados.idAdmin())
                .orElseThrow(() -> new IllegalArgumentException("Administrador não encontrado"));

        // Cria o objeto Emprestimo associando as entidades gerenciadas pelo JPA
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuario(usuario);
        emprestimo.setExemplar(exemplar);
        emprestimo.setAdministrador(administrador);
        emprestimo.setDataEmprestimo(LocalDateTime.now());
        emprestimo.setDataDevolucaoPrevista(LocalDate.now().plusDays(7));
        emprestimo.setStatus(StatusEmprestimo.ATIVO);

        return emprestimoRepository.save(emprestimo);
    }
}