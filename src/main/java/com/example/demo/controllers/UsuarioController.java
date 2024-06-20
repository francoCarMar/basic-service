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
    public ResponseEntity<Object> registrar(@RequestBody Usuario usuario) {
        try{
            Usuario usr = usuarioService.registrar(usuario);
            return new ResponseEntity<>(usr, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/dni/{id}")
    public ResponseEntity<UsuarioDto> getUsuario(@PathVariable Long id){
        try {
            return new ResponseEntity<>(usuarioService.getUsuario(id), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioDto> getUsuario(@PathVariable String correo){
        try {
            return new ResponseEntity<>(usuarioService.getUsuarioByCorreo(correo), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
