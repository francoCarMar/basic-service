package com.example.demo.services;

import com.example.demo.entities.Usuario;
import com.example.demo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean login(Long dni, String password) {
        if(usuarioRepository.existsById(dni)){
           Usuario usuario = usuarioRepository.findById(dni).get();
           return usuario.getPassword().equals(password);
        }return false;
    }

    public Usuario registrar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
