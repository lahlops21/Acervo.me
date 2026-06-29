package br.com.acervo.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.acervo.api.model.usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
 
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCodigo(String codigo);
}
