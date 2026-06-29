package br.com.acervo.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acervo.api.model.administrador.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer>{
   Optional<Administrador> findByEmail(String email);
}
