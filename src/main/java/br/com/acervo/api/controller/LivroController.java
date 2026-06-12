package br.com.acervo.api.controller;

import org.springframework.web.bind.annotation.*;
import br.com.acervo.api.model.livro.DadosCadastroLivro;


@RestController // Spring Web - Informa para o Spring Boot que a classe é um controller(GET/POST/PUT/DELETE) pois não temos front-end. 
@RequestMapping("medicos") // SPRING WEB - Cria um caminho (end-point) para a classe abaixo
public class LivroController {
    // métodos -> funções -> ações 
    //GET 
    //POST
    @PostMapping // SPRING WEB - Informa que o método abaixo é do tipo POST (Cadastrar)
    public void cadastrar(@RequestBody String dados){

        System.out.println(dados);
    }
    
    //PUT
    //DELETE 
    

}