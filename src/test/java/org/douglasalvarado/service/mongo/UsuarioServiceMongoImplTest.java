package org.douglasalvarado.service.mongo;

import org.douglasalvarado.dto.UsuarioDto;
import org.douglasalvarado.model.mongo.UsuarioMongo;
import org.douglasalvarado.repository.mongo.UsuarioMongoRepository;
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

class UsuarioServiceMongoImplTest {

    @Mock
    private UsuarioMongoRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceMongoImpl usuarioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test para crear un usuario exitosamente
    @Test
    void createUsuarioSuccessfully() {
        UsuarioMongo usuario = new UsuarioMongo("1", "John Doe", "johndoe@example.com", "password123");
        UsuarioDto usuarioDto = new UsuarioDto((long)1, "John Doe", "johndoe@example.com", "password123");
        when(usuarioRepository.save(any(UsuarioMongo.class))).thenReturn(usuario);

        UsuarioDto result = usuarioService.createUsuario(usuarioDto);

        assertNotNull(result);
        assertEquals("John Doe", result.getNombre());
    }

    // Test para listar todos los usuarios
    @Test
    void listAllUsuarios() {
        List<UsuarioMongo> usuarios = List.of(new UsuarioMongo("1", "John Doe", "johndoe@example.com", "password123"));
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioDto> result = usuarioService.getAllUsuarios();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getNombre());
    }

    // Test para encontrar un usuario por correo exitosamente
    @Test
    void findByCorreoSuccessfully() {
        UsuarioMongo usuario = new UsuarioMongo("1", "John Doe", "johndoe@example.com", "password123");
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
        UsuarioMongo updatedUsuario = new UsuarioMongo("1", "John Updated", "johndoe@example.com", "password123");
        UsuarioDto usuarioDto = new UsuarioDto((long)1, "John Updated", "johndoe@example.com", "password123");
        
        when(usuarioRepository.existsById("1")).thenReturn(true);
        when(usuarioRepository.save(any(UsuarioMongo.class))).thenReturn(updatedUsuario);

        UsuarioDto result = usuarioService.updateUsuario((long)1, usuarioDto);

        assertNotNull(result);
        assertEquals("John Updated", result.getNombre());
    }


    // Test para manejar cuando un usuario a actualizar no es encontrado
    @Test
    void updateUsuarioNotFound() {
        when(usuarioRepository.existsById("1")).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.updateUsuario((long)1, new UsuarioDto());
        });

        assertEquals("Usuario no encontrado con ID: 1", exception.getMessage());
    }

    // Test para eliminar un usuario exitosamente
    @Test
    void deleteUsuarioSuccessfully() {
        doNothing().when(usuarioRepository).deleteById("1");

        usuarioService.deleteUsuario((long)1);

        verify(usuarioRepository, times(1)).deleteById("1");
    }

    // Test para obtener un usuario exitosamente
    @Test
    void getUsuarioSuccessfully() {
        UsuarioMongo usuario = new UsuarioMongo("1", "John Doe", "johndoe@example.com", "password123");
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));

        UsuarioDto result = usuarioService.getUsuario((long)1);

        assertNotNull(result);
        assertEquals(null, result.getId());
        assertEquals("John Doe", result.getNombre());
        assertEquals("johndoe@example.com", result.getCorreo());
    }

    // Test para manejar cuando un usuario no es encontrado
    @Test
    void getUsuarioNotFound() {
        when(usuarioRepository.findById("1")).thenReturn(Optional.empty());

        UsuarioDto result = usuarioService.getUsuario((long)1);

        assertNull(result);
    }
}
