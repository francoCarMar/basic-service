package com.example.demo;

import com.example.demo.dtos.UsuarioDto;
import com.example.demo.entities.Usuario;
import com.example.demo.mappers.UsuarioMapper;
import com.example.demo.repositories.UsuarioRepository;
import com.example.demo.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UsuarioServiceTest {
    private UsuarioService usuarioService;
    private UsuarioRepository usuarioRepository;
    private UsuarioMapper usuarioMapper;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario((long) 1, (long) 1, "nombre", "apellido", "correo", "123456789");
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioMapper = new UsuarioMapper();
        usuarioService = new UsuarioService(usuarioRepository, usuarioMapper);
    }

    @Test
    void testRegistrarUsuario() {
        when(usuarioRepository.existsByCorreo(usuario.getCorreo())).thenReturn(false);
        when(usuarioRepository.existsByDni(usuario.getDni())).thenReturn(false);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        UsuarioDto dto = usuarioMapper.usuarioToUsuarioDto(usuario);
        UsuarioDto dtoRegistro = usuarioService.registrar(usuario);
        assertEquals(dtoRegistro, dto);
    }

    @Test
    void testRegistrarUsuarioUnsuccessfullExistCorreo() {
        when(usuarioRepository.existsByCorreo(usuario.getCorreo())).thenReturn(true);
        Exception exception = assertThrows(RuntimeException.class, () -> usuarioService.registrar(usuario));
        assertEquals("Correo registrado con anterioridad", exception.getMessage());
    }

    @Test
    void testRegistrarUsuarioUnsuccessfulExistDni() {
        when(usuarioRepository.existsByCorreo(usuario.getCorreo())).thenReturn(false);
        when(usuarioRepository.existsByDni(usuario.getDni())).thenReturn(true);
        assertThrows(RuntimeException.class, () -> usuarioService.registrar(usuario));
    }

    @Test
    void login() {
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(java.util.Optional.of(usuario));
        UsuarioDto dto = usuarioMapper.usuarioToUsuarioDto(usuario);
        UsuarioDto dtoLogin = usuarioService.login(usuario.getCorreo(), usuario.getPassword());
        assertEquals(dtoLogin, dto);
    }

    @Test
    void loginUnsuccessfulPasswordIncorrect() {
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(java.util.Optional.of(usuario));
        String correo = usuario.getCorreo(), password = usuario.getPassword() + "1";
        Exception exception = assertThrows(RuntimeException.class, () -> usuarioService.login(correo, password));
        assertEquals("Contraseña incorrecta", exception.getMessage());
    }

    @Test
    void loginUnsuccessfulCorreoIncorrect() {
        String correo = "correoNoExistente", password = usuario.getPassword();
        Exception exception = assertThrows(RuntimeException.class, () -> usuarioService.login(correo,password));
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void getUsuarioByDni() {
        when(usuarioRepository.findByDni(usuario.getDni())).thenReturn(java.util.Optional.of(usuario));
        UsuarioDto dto = usuarioMapper.usuarioToUsuarioDto(usuario);
        UsuarioDto dtoUsuario = usuarioService.getUsuario(usuario.getDni());
        assertEquals(dtoUsuario, dto);
    }

    @Test
    void getUsuarioByDniUnsuccessful() {
        // dni no existente
        assertThrows(RuntimeException.class, () -> usuarioService.getUsuario((long) 123));
    }

    @Test
    void getUsuarioByCorreo() {
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(java.util.Optional.of(usuario));
        UsuarioDto dto = usuarioMapper.usuarioToUsuarioDto(usuario);
        UsuarioDto dtoUsuario = usuarioService.getUsuarioByCorreo(usuario.getCorreo());
        assertEquals(dtoUsuario, dto);
    }

    @Test
    void getUsuarioByCorreoUnssuccful() {
        // correo no existente
        assertThrows(RuntimeException.class, () -> usuarioService.getUsuarioByCorreo("correoNoExistente"));
    }

    @Test
    void UpdateUsuarioDiferentDni() {
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(java.util.Optional.of(usuario));
        UsuarioDto dto = new UsuarioDto(usuario.getNombre(), usuario.getApellido(), usuario.getCorreo(), (long)5);
        UsuarioDto dtoUsuario = usuarioService.updateUsuario(dto, usuario.getCorreo());
        assertEquals(dtoUsuario, dto);
    }

    @Test
    void UpdateUsuarioEqualDni() {
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(java.util.Optional.of(usuario));
        UsuarioDto dto = new UsuarioDto(usuario.getNombre() + "update", usuario.getApellido(), usuario.getCorreo(), usuario.getDni());
        UsuarioDto dtoUsuario = usuarioService.updateUsuario(dto, usuario.getCorreo());
        assertEquals(dtoUsuario, dto);
    }
    @Test
    void UpdateUsuarioFailed() {
        UsuarioDto dto = new UsuarioDto("nombre", "apellido", "correo", (long) 1);
        Exception exception = assertThrows(RuntimeException.class, () -> usuarioService.updateUsuario(dto, "correoNoExistente"));
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

}