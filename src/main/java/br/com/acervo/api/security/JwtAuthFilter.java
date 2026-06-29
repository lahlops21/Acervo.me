package br.com.acervo.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import br.com.acervo.api.config.UsuarioDetailsService;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioDetailsService userDetailsService;

    // MÉTODO PARA CORRIGIR O ERRO 403:
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        String method = request.getMethod();
        
        // 🚀 CORREÇÃO CIRÚRGICA: Se a requisição for um OPTIONS (CORS Preflight), dá passagem livre sem ler Token!
        if ("OPTIONS".equalsIgnoreCase(method)) {
            return true;
        }

        // Se for cadastrar usuário leitor, fazer login ou cadastrar autor, o filtro ignora e dá passagem 
        return (path.startsWith("/usuarios") && method.equals("POST")) 
            || path.startsWith("/auth/login")
            || (path.startsWith("/autores") && method.equals("POST"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
         // Se for OPTIONS, retorna status 200 OK imediatamente e encerra o filtro
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        String roleDoToken = null;

        // Extrai o token do padrão "Bearer <token>"
       if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                var claims = jwtUtil.extrairClaims(token);
                username = claims.getSubject(); 
                roleDoToken = claims.get("role", String.class); //  Captura a ROLE_ADMIN gravada no token
            } catch (Exception e) {
                logger.warn("Token JWT inválido, malformatado ou expirado");
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.isTokenValido(token, userDetails.getUsername())) {
                
                // 🚀 SOLUÇÃO DO 403: Se encontramos a Role dentro do token, criamos a autoridade explicitamente!
                java.util.List<org.springframework.security.core.GrantedAuthority> autoridades;
                if (roleDoToken != null) {
                    autoridades = java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority(roleDoToken));
                } else {
                    autoridades = (java.util.List<org.springframework.security.core.GrantedAuthority>) userDetails.getAuthorities();
                }

                // Injeta as autoridades validadas do Token na sessão do Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, autoridades); // 👈 Passa as autoridades forçadas aqui
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}