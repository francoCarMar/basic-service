package com.example.demo.controllers;

import com.example.demo.dtos.LoginDto;
import com.example.demo.dtos.UsuarioDto;
import com.example.demo.entities.Usuario;
import com.example.demo.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto logindto) {
        String correo = logindto.correo();
        String password = logindto.password();
        if(usuarioService.login(correo,password))
            return new ResponseEntity<>( HttpStatus.OK);
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @PostMapping("/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario registrar(@RequestBody Usuario usuario) {
        return usuarioService.registrar(usuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> getUsuario(@PathVariable Long id){
        try {
            UsuarioDto usuario = usuarioService.getUsuario(id);
            return new ResponseEntity<>(usuarioService.getUsuario(id), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
