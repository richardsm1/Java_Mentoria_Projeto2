package com.mentorias.login.repositories;

import com.mentorias.login.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorie extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuario(String usuario);
}
