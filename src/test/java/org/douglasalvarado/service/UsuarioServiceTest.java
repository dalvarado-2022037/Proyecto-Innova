package org.douglasalvarado.service;

import org.douglasalvarado.dto.UsuarioDto;
import org.douglasalvarado.model.Usuario;
import org.douglasalvarado.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private UsuarioDto usuarioDto;

    //Configuraciones para un Dto y un Model
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = Usuario.builder()
                .id("1")
                .nombre("John Doe")
                .correo("john@example.com")
                .password("password123")
                .build();

        usuarioDto = UsuarioDto.builder()
                .id("1")
                .nombre("John Doe")
                .correo("john@example.com")
                .password("password123")
                .build();
    }

    //Crear un usuario
    @Test
    void testCreateUsuario() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioDto createdUsuario = usuarioService.createUsuario(usuarioDto);

        assertNotNull(createdUsuario);
        assertEquals(usuarioDto.getId(), createdUsuario.getId());
        assertEquals(usuarioDto.getNombre(), createdUsuario.getNombre());
        assertEquals(usuarioDto.getCorreo(), createdUsuario.getCorreo());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    //Obtener un usuario
    @Test
    void testGetUsuario() {
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));

        UsuarioDto foundUsuario = usuarioService.getUsuario("1");

        assertNotNull(foundUsuario);
        assertEquals(usuarioDto.getId(), foundUsuario.getId());
        verify(usuarioRepository, times(1)).findById("1");
    }

    //Actualizar un usuario
    @Test
    void testUpdateUsuario() {
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        usuarioDto.setNombre("Updated Name");
        UsuarioDto updatedUsuario = usuarioService.updateUsuario("1", usuarioDto);

        assertNotNull(updatedUsuario);
        assertEquals("Updated Name", updatedUsuario.getNombre());
        verify(usuarioRepository, times(1)).findById("1");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    //Actualizar un usuario que no existe
    @Test
    void testUpdateUsuarioReturnNull() {
        when(usuarioRepository.findById("1")).thenReturn(Optional.empty());

        UsuarioDto result = usuarioService.updateUsuario("1", usuarioDto);

        assertNull(result);
        verify(usuarioRepository, times(1)).findById("1");
        verify(usuarioRepository, times(0)).save(any(Usuario.class));
    }

    //Eliminar un usuario
    @Test
    void testDeleteUsuario() {
        doNothing().when(usuarioRepository).deleteById("1");

        usuarioService.deleteUsuario("1");

        verify(usuarioRepository, times(1)).deleteById("1");
    }

    //Verificar si existe el correo
    @Test
    void testExistsByCorreo() {
        when(usuarioRepository.existsByCorreo("john@example.com")).thenReturn(true);

        boolean exists = usuarioService.existsByCorreo("john@example.com");

        assertTrue(exists);
        verify(usuarioRepository, times(1)).existsByCorreo("john@example.com");
    }

    //Obtener todos los usuarios
    @Test
    void testGetAllUsuarios() {
        List<Usuario> usuarios = List.of(usuario);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioDto> result = usuarioService.getAllUsuarios();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(usuarioDto.getId(), result.get(0).getId());
        verify(usuarioRepository, times(1)).findAll();
    }

}
