package org.douglasalvarado.service.postgres;

import org.douglasalvarado.dto.UsuarioDto;
import org.douglasalvarado.model.postgres.UsuarioPostgres;
import org.douglasalvarado.repository.postgres.UsuarioPostgresRepository;
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

class UsuarioServicePostgresImplTest {

    @Mock
    private UsuarioPostgresRepository usuarioRepository;

    @InjectMocks
    private UsuarioServicePostgresImpl usuarioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test para crear un usuario exitosamente
    @Test
    void createUsuarioSuccessfully() {
        UsuarioPostgres usuario = new UsuarioPostgres((long)1, "John Doe", "johndoe@example.com", "password123");
        UsuarioDto usuarioDto = new UsuarioDto((long)1, "John Doe", "johndoe@example.com", "password123");
        when(usuarioRepository.save(any(UsuarioPostgres.class))).thenReturn(usuario);

        UsuarioDto result = usuarioService.createUsuario(usuarioDto);

        assertNotNull(result);
        assertEquals("John Doe", result.getNombre());
    }

    // Test para listar todos los usuarios
    @Test
    void listAllUsuarios() {
        List<UsuarioPostgres> usuarios = List.of(new UsuarioPostgres((long)1, "John Doe", "johndoe@example.com", "password123"));
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioDto> result = usuarioService.getAllUsuarios();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getNombre());
    }

    // Test para encontrar un usuario por correo exitosamente
    @Test
    void findByCorreoSuccessfully() {
        UsuarioPostgres usuario = new UsuarioPostgres((long)1, "John Doe", "johndoe@example.com", "password123");
        when(usuarioRepository.findByCorreo("johndoe@example.com")).thenReturn(usuario);

        UsuarioDto result = usuarioService.findByCorreo("johndoe@example.com");

        assertNotNull(result);
        assertEquals("John Doe", result.getNombre());
    }

    // Test para verificar si un correo existe
    @Test
    void existsByCorreo() {
        when(usuarioRepository.existsByCorreo("johndoe@example.com")).thenReturn(true);

        boolean exists = usuarioService.existsByCorreo("johndoe@example.com");

        assertTrue(exists);
    }

    // Test para actualizar un usuario exitosamente
    @Test
    void updateUsuarioSuccessfully() {
        UsuarioPostgres updatedUsuario = new UsuarioPostgres((long)1, "John Updated", "johndoe@example.com", "password123");
        UsuarioDto usuarioDto = new UsuarioDto((long)1, "John Updated", "johndoe@example.com", "password123");
        
        when(usuarioRepository.existsById((long)1)).thenReturn(true);
        when(usuarioRepository.save(any(UsuarioPostgres.class))).thenReturn(updatedUsuario);

        UsuarioDto result = usuarioService.updateUsuario((long)1, usuarioDto);

        assertNotNull(result);
        assertEquals("John Updated", result.getNombre());
    }

    // Test para manejar cuando un usuario a actualizar no es encontrado
    @Test
    void updateUsuarioNotFound() {
        when(usuarioRepository.existsById((long)1)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.updateUsuario((long)1, new UsuarioDto());
        });

        assertEquals("Usuario no encontrado con ID: 1", exception.getMessage());
    }

    // Test para eliminar un usuario exitosamente
    @Test
    void deleteUsuarioSuccessfully() {
        doNothing().when(usuarioRepository).deleteById((long)1);

        usuarioService.deleteUsuario((long)1);

        verify(usuarioRepository, times(1)).deleteById((long)1);
    }

    // Test para obtener un usuario exitosamente
    @Test
    void getUsuarioSuccessfully() {
        UsuarioPostgres usuario = new UsuarioPostgres((long)1, "John Doe", "johndoe@example.com", "password123");
        when(usuarioRepository.findById((long)1)).thenReturn(Optional.of(usuario));

        UsuarioDto result = usuarioService.getUsuario((long)1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John Doe", result.getNombre());
        assertEquals("johndoe@example.com", result.getCorreo());
    }

    // Test para manejar cuando un usuario no es encontrado
    @Test
    void getUsuarioNotFound() {
        when(usuarioRepository.findById((long)1)).thenReturn(Optional.empty());

        UsuarioDto result = usuarioService.getUsuario((long)1);

        assertNull(result);
    }
}
