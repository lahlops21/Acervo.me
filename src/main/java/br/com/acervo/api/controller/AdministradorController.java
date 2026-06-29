package br.com.acervo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.acervo.api.model.administrador.Administrador;
import br.com.acervo.api.model.administrador.DadosCadastroAdministrador;
import br.com.acervo.api.repository.AdministradorRepository;
import jakarta.transaction.Transactional;

@RestController 
@RequestMapping("administradores")
@CrossOrigin(origins = "*") 
public class AdministradorController {

    @Autowired
    private AdministradorRepository repository; // Injeta o acesso ao banco H2

    @PostMapping 
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody DadosCadastroAdministrador dados) {
        // 1. Cria o objeto Administrador passando o Record com os dados do Insomnia
        var administrador = new Administrador(dados, dados.senhaRaw());

        // 2. Salva efetivamente na tabela do banco H2
        repository.save(administrador);

        // 3. Devolve uma resposta de sucesso para o cliente
        return ResponseEntity.ok("Administrador cadastrado com sucesso! ID gerado: " + administrador.getId());
    }
}
