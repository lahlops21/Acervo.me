package br.com.acervo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.acervo.api.model.autor.Autor;
import br.com.acervo.api.model.autor.DadosCadastroAutor;
import br.com.acervo.api.model.autor.DadosDetalhamentoAutor;
import br.com.acervo.api.repository.AutorRepository;
import jakarta.transaction.Transactional;



@RestController // Spring Web - Informa para o Spring Boot que a classe é um controller(GET/POST/PUT/DELETE) pois não temos front-end. 
@RequestMapping("autores") // SPRING WEB - Cria um caminho (end-point) para a classe abaixo
@CrossOrigin(origins = "*")
public class AutorController {

    @Autowired
    private AutorRepository repository;

    @PostMapping // SPRING WEB - Informa que o método abaixo é do tipo POST (Cadastrar)
    @Transactional
    public ResponseEntity<DadosDetalhamentoAutor> cadastrar(@RequestBody DadosCadastroAutor dados){
        // Cria o objeto Autor a partir do DTO de entrada  //  Passa a variável 'dados' para o construtor do Autor!
    var autor = new Autor(dados);
        

        // Salvamos no banco h2
        repository.save(autor);

        // Devolve o DTO de saída com o ID gerado pelo banco e o status 201 (Created)
        return ResponseEntity.ok(new DadosDetalhamentoAutor(autor));
        }
       
    @GetMapping // Listar os autores cadastrados
    public ResponseEntity<List<DadosDetalhamentoAutor>> listar() {
        // Busca todos, transforma em DTO de saída e devolve para quem pediu
        var lista = repository.findAll().stream().map(DadosDetalhamentoAutor::new).toList();
        return ResponseEntity.ok(lista);
    }

    }
    
    //PUT
    //DELETE 
    



