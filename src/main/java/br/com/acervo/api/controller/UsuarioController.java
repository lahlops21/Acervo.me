package br.com.acervo.api.controller;


import java.util.List;
import org.springframework.web.bind.annotation.*;
import br.com.acervo.api.model.usuario.Usuario;

@RestController // Spring Web - Informa para o Spring Boot que a classe é um controller(GET/POST/PUT/DELETE) pois não temos front-end. 
@RequestMapping("usuario") // SPRING WEB - Cria um caminho (end-point) para a classe abaixo
public class UsuarioController {
    // métodos -> funções -> ações 
    
    @PostMapping("/adicionarUsuario") // SPRING WEB - Informa que o método abaixo é do tipo POST
    public void cadastrarUsuario(String dados){

        System.out.println("Usuario Cadastrado com sucesso" + dados);
    }
    
    @GetMapping("/listarUsuario")
    public String verUsuario(){ // mudar depois para List
      return "Usuarios";
    }
    
    // @GetMapping("/obterUsuarioPeloId/{IdUsuario}")
    // public Usuario obterUsuarioPorId(Integer idusuario){
    //   return ; 
    // }
    

}