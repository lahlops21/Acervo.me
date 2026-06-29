package br.com.acervo.api.config;

import br.com.acervo.api.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

        @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> cors.configure(http)) // Lê as configurações do seu CorsConfig.java
            .csrf(csrf -> csrf.disable()) // 🚀 CORREÇÃO DEFINITIVA: Desabilita a trava CSRF para liberar os métodos POST/PUT/DELETE externos!
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define API Stateless
            .authorizeHttpRequests(req -> {
                // Dá permissão livre imediata para a checagem prévia do Axios (CORS)
                req.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll(); 
                
                // Rotas totalmente públicas
                req.requestMatchers(HttpMethod.POST, "/auth/login").permitAll(); 
                req.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();   
                req.requestMatchers(HttpMethod.POST, "/autores/**").permitAll(); 
                req.requestMatchers("/h2-console/**").permitAll();
                
                req.requestMatchers("/emprestimos/**").authenticated();
                
                req.anyRequest().authenticated(); // Todo o resto exige autenticação
            })
            // 🚀 Garante que o filtro do JWT trate o Token antes de qualquer validação interna de rotas
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 👈 Criptografia BCrypt oficial exigida pelas tabelas
    }
}
