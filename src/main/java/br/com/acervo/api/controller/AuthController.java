package br.com.acervo.api.controller;

import br.com.acervo.api.config.UsuarioDetailsService;
import br.com.acervo.api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UsuarioDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> dados) {
        String email = dados.get("email");
        String senha = dados.get("senha");

        // O Spring Security valida as credenciais no banco automaticamente usando BCrypt
        var authenticationToken = new UsernamePasswordAuthenticationToken(email, senha);
        var authentication = manager.authenticate(authenticationToken);

        var principal = (UserDetails) authentication.getPrincipal();
        
        // Extrai a role atribuída para enviar ao React Native
        String role = principal.getAuthorities().iterator().next().getAuthority();
        
        // Gera o token assinado usando o componente do seu professor
        String tokenJwt = jwtUtil.gerarToken(principal.getUsername(), role);

        Integer idReal = null; 
        String codigoExibicao = "ADMIN";
      
        
       // 🚀 CAPTURA O ID REAL DO BANCO DE DADOS DINAMICAMENTE
        if (principal instanceof br.com.acervo.api.model.usuario.Usuario) {
            var user = (br.com.acervo.api.model.usuario.Usuario) principal;
            codigoExibicao = user.getCodigo();
            idReal = user.getId();
        } else if (principal instanceof br.com.acervo.api.model.administrador.Administrador) {
            var admin = (br.com.acervo.api.model.administrador.Administrador) principal;
            idReal = admin.getId(); // Pega o ID numérico gerado pelo MySQL
        }

        return ResponseEntity.ok(Map.of(
            "token", tokenJwt, 
            "role", role, 
            "email", email,
            "codigo", codigoExibicao,
            "id", idReal // Envia o ID numérico da chave primária para o app guardar
        ));
    }
}
