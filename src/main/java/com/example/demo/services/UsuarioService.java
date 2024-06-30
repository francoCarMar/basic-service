package com.example.demo.services;

import com.example.demo.dtos.UsuarioDto;
import com.example.demo.entities.Usuario;
import com.example.demo.mappers.UsuarioMapper;
import com.example.demo.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public UsuarioDto login(String correo, String password) {
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        validatePassword(usuario, password);
        return usuarioMapper.usuarioToUsuarioDto(usuario);
    }

    private void validatePassword(Usuario usuario, String password){
        if(!usuario.getPassword().equals(password))
            throw new RuntimeException("Contraseña incorrecta");
    }

    public UsuarioDto registrar(Usuario usuario) {
        if(usuarioRepository.existsByCorreo(usuario.getCorreo()))
            throw new RuntimeException("Correo registrado con anterioridad");
        if(usuarioRepository.existsById(usuario.getDni()))
            throw new RuntimeException("Dni registrado con anterioridad");
        return usuarioMapper.usuarioToUsuarioDto(usuarioRepository.save(usuario));
    }

    public UsuarioDto getUsuario(Long dni){
        Usuario usuario = usuarioRepository.findByDni(dni).orElseThrow();
        return usuarioMapper.usuarioToUsuarioDto(usuario);
    }

    public UsuarioDto getUsuarioByCorreo(String correo){
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElseThrow();
        return usuarioMapper.usuarioToUsuarioDto(usuario);
    }

    @Transactional
    public UsuarioDto updateUsuario(UsuarioDto usuarioDto, String correo){
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElseThrow();
        usuario.setCorreo(usuarioDto.correo());
        usuario.setNombre(usuarioDto.nombre());
        usuario.setApellido(usuarioDto.apellido());
        if(usuarioDto.dni() != usuario.getDni())
            usuario.setDni(usuarioDto.dni());
        usuarioRepository.save(usuario);
        return usuarioDto;
    }

}
