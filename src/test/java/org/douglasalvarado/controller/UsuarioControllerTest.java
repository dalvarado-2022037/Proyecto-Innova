package org.douglasalvarado.controller;

import org.douglasalvarado.dto.UsuarioDto;
import org.douglasalvarado.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private MockMvc mockMvc;

    public UsuarioControllerTest() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    // Obtener todos los usuarios correcto
    @Test
    void testGetAllUsuarios() throws Exception {
        List<UsuarioDto> usuarios = Arrays.asList(new UsuarioDto(), new UsuarioDto());
        when(usuarioService.getAllUsuarios()).thenReturn(usuarios);

        mockMvc.perform(get("/usuario/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // Crear un usuario correcto
    @Test
    void testCreateUsuario() throws Exception {
        UsuarioDto usuarioDto = new UsuarioDto();
        when(usuarioService.createUsuario(any(UsuarioDto.class))).thenReturn(usuarioDto);

        mockMvc.perform(post("/usuario/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"valor\", \"correo\": \"email@example.com\", \"password\": \"12345\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // Crear un usuario Internal Server Error
    @Test
    void testCreateUsuarioServerError() throws Exception {
        when(usuarioService.createUsuario(any(UsuarioDto.class)))
                .thenThrow(new RuntimeException("Error al crear el usuario"));

        mockMvc.perform(post("/usuario/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"valor\", \"correo\": \"email@example.com\", \"password\": \"12345\"}"))
                .andExpect(status().isInternalServerError());
    }

    // Buscar un usuario por id Not Found
    @Test
    void testGetUsuarioByIdNotFound() throws Exception {
        when(usuarioService.getUsuario(any(String.class))).thenReturn(null);

        mockMvc.perform(get("/usuario/find-by/123"))
                .andExpect(status().isNotFound());
    }

    // Actualizar un usuario correcto
    @Test
    void testUpdateUsuario() throws Exception {
        UsuarioDto updatedUsuario = new UsuarioDto();
        when(usuarioService.updateUsuario(any(String.class), any(UsuarioDto.class))).thenReturn(updatedUsuario);

        mockMvc.perform(put("/usuario/update/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"nuevo\", \"correo\": \"email@example.com\", \"password\": \"12345\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // Actualizar un usuario Bad Request
    @Test
    void testUpdateUsuarioBadRequest() throws Exception {
        when(usuarioService.updateUsuario(any(String.class), any(UsuarioDto.class)))
                .thenThrow(new IllegalArgumentException());

        mockMvc.perform(put("/usuario/update/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"nuevo\", \"correo\": \"email@example.com\", \"password\": \"12345\"}"))
                .andExpect(status().isBadRequest());
    }

    // Actualizar un usuario Internal Server Error
    @Test
    void testUpdateUsuarioServerError() throws Exception {
        when(usuarioService.updateUsuario(any(String.class), any(UsuarioDto.class)))
                .thenThrow(new RuntimeException("Error al actualizar el usuario"));

        mockMvc.perform(put("/usuario/update/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"nuevo\", \"correo\": \"email@example.com\", \"password\": \"12345\"}"))
                .andExpect(status().isInternalServerError());
    }

    // Eliminar un usuario correcto
    @Test
    void testDeleteUsuario() throws Exception {
        mockMvc.perform(delete("/usuario/delete/123"))
                .andExpect(status().isOk());
    }

    // Eliminar un usuario Internal Server Error
    @Test
    void testDeleteUsuarioServerError() throws Exception {
        doThrow(new RuntimeException()).when(usuarioService).deleteUsuario(any(String.class));

        mockMvc.perform(delete("/usuario/delete/123"))
                .andExpect(status().isInternalServerError());
    }
}
