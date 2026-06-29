package br.com.acervo.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.acervo.api.repository.UsuarioRepository;
import br.com.acervo.api.repository.AdministradorRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService { 

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Tenta buscar na tabela de Usuários comuns
        var usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            return usuario.get();
        }

        // 2. Se não achar, tenta buscar na tabela de Administradores
        var admin = administradorRepository.findByEmail(email);
        if (admin.isPresent()) {
            return admin.get();
        }

        throw new UsernameNotFoundException("E-mail não cadastrado no acervo: " + email);
    }
}
