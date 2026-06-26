package br.com.acervo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.acervo.api.model.emprestimo.DadosCadastroEmprestimo;
import br.com.acervo.api.model.emprestimo.DadosDetalhamentoEmprestimo;
import br.com.acervo.api.service.EmprestimoService;
import jakarta.transaction.Transactional;

@RestController 
@RequestMapping("emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService; // Aciona o cérebro do sistema

    @PostMapping 
    @Transactional
    public ResponseEntity<DadosDetalhamentoEmprestimo> cadastrar(@RequestBody DadosCadastroEmprestimo dados) {
        // Chama as regras de negócio do Service
        var emprestimo = emprestimoService.abrirEmprestimo(dados);
        
        // Devolve os dados detalhados para o Insomnia
        return ResponseEntity.ok(new DadosDetalhamentoEmprestimo(emprestimo));
    }
}



