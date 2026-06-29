package br.com.acervo.api.config; // 👈 Ajuste para o seu pacote oficial

import br.com.acervo.api.model.administrador.Administrador;
import br.com.acervo.api.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CargaInicialData implements CommandLineRunner {

    @Autowired
    private AdministradorRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // 👈 O motor oficial de criptografia do seu projeto

    @Override
    public void run(String... args) throws Exception {
        
        // Verifica se a tabela está vazia para criar o admin oficial com o hash perfeito
        if (adminRepository.findByEmail("admin@acervo.me").isEmpty()) {
            
            // Gera o hash usando a exata mesma máquina que o sistema usa
            String senhaAdminHasheada = passwordEncoder.encode("admin123");
            
            Administrador admin = new Administrador();
            admin.setNome("Bibliotecário Chefe");
            admin.setEmail("admin@acervo.me");
            admin.setSenhaHash(senhaAdminHasheada);
            
            adminRepository.save(admin);
            System.out.println("🚀 [CARGA INICIAL] Administrador criado com o hash oficial da aplicação!");
        }
    }
}
