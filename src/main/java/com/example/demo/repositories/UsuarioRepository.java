package com.example.demo.repositories;

import com.example.demo.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByDni(Long dni);
    boolean existsByCorreo(String correo);
    boolean existsByDni(Long dni);
    void deleteByCorreo(String correo);
}
