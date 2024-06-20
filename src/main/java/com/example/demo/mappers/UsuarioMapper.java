package com.example.demo.mappers;

import com.example.demo.dtos.UsuarioDto;
import com.example.demo.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public UsuarioDto usuarioToUsuarioDto(Usuario usuario){
        return new UsuarioDto(usuario.getNombre(), usuario.getApellido(), usuario.getCorreo());
    }
}
