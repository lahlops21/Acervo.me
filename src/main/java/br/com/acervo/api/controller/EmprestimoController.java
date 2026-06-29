package br.com.acervo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.acervo.api.model.emprestimo.DadosCadastroEmprestimo;
import br.com.acervo.api.model.emprestimo.DadosDetalhamentoEmprestimo;
import br.com.acervo.api.service.EmprestimoService;
import jakarta.transaction.Transactional;

@RestController 
@RequestMapping("emprestimos")
@CrossOrigin(origins = "*")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService; // Aciona o cérebro do sistema

   @PostMapping 
    public ResponseEntity<?> cadastrar(@RequestBody DadosCadastroEmprestimo dados) {
        try {
        // O Service roda sua própria transação isolada
        var emprestimo = emprestimoService.abrirEmprestimo(dados);
        return ResponseEntity.ok(new DadosDetalhamentoEmprestimo(emprestimo));
        } catch (IllegalStateException | IllegalArgumentException e) {
            // Agora sim o catch vai funcionar lindo e vai devolver o texto exato do erro para o React Native!
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

@GetMapping // Define que requisições GET para /emprestimos vão acionar este método
public ResponseEntity<List<DadosDetalhamentoEmprestimo>> listar() {
    // Chama o Service para trazer a lista de DTOs mastigada
    var listaDeEmprestimos = emprestimoService.listarTodosOsEmprestimos();
    
    // Retorna a lista com o status HTTP 200 OK
    return ResponseEntity.ok(listaDeEmprestimos);
}

@PutMapping("/{id}/devolucao") // 🌟 NOVO: Endpoint responsável por processar a devolução física
    public ResponseEntity<DadosDetalhamentoEmprestimo> devolver(@PathVariable Integer id) {
        // Aciona o service para rodar o fluxo completo de liberação em cascata
        var emprestimoDevolvido = emprestimoService.devolverEmprestimo(id);
        
        // Retorna a ficha técnica atualizada com a data de entrega real e o status DEVOLVIDO
        return ResponseEntity.ok(new DadosDetalhamentoEmprestimo(emprestimoDevolvido));
    }

}



