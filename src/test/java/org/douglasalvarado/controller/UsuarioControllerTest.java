package org.douglasalvarado.controller;

import org.douglasalvarado.dto.UsuarioDto;
import org.douglasalvarado.interfaces.UsuarioService;
import org.douglasalvarado.service.UsuarioDatabaseServiceSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UsuarioControllerTest {

        @Mock
        private UsuarioDatabaseServiceSelector usuarioServiceSelector;

        @InjectMocks
        private UsuarioController usuarioController;

        @Mock
        private UsuarioService usuarioService;

        private MockMvc mockMvc;

        @BeforeEach
        public void setup() {
                MockitoAnnotations.openMocks(this);
                when(usuarioServiceSelector.getUsuarioService()).thenReturn(usuarioService);
                mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
        }

        // Test para crear un usuario exitosamente
        @Test
        void testCreateUsuario() throws Exception {
                UsuarioDto usuarioDto = new UsuarioDto((long)1, "Test User", "test@test.com", "hashed_password");

                when(usuarioServiceSelector.getUsuarioService().createUsuario(any(UsuarioDto.class)))
                                .thenReturn(usuarioDto);

                mockMvc.perform(post("/usuario/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"nombre\": \"Test User\", \"correo\": \"test@test.com\", \"password\": \"hashed_password\"}"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nombre").value("Test User"))
                                .andExpect(jsonPath("$.correo").value("test@test.com"));
        }

        // Test para obtener un usuario por ID exitosamente
        @Test
        void testGetUsuario() throws Exception {
                UsuarioDto usuarioDto = new UsuarioDto((long)1, "Test User", "test@test.com", "hashed_password");

                when(usuarioServiceSelector.getUsuarioService().getUsuario((long)1)).thenReturn(usuarioDto);

                mockMvc.perform(get("/usuario/find-by/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nombre").value("Test User"))
                                .andExpect(jsonPath("$.correo").value("test@test.com"));
        }

        // Test para obtener un usuario que no existe
        @Test
        void testGetUsuarioNotFound() throws Exception {
                when(usuarioServiceSelector.getUsuarioService().getUsuario((long)2)).thenReturn(null);

                mockMvc.perform(get("/usuario/find-by/2"))
                                .andExpect(status().isNotFound());
        }

        // Test para listar todos los usuarios exitosamente
        @Test
        void testGetAllUsuarios() throws Exception {
                List<UsuarioDto> usuarios = List.of(
                                new UsuarioDto((long)1, "User One", "one@test.com", "password1"),
                                new UsuarioDto((long)1, "User Two", "two@test.com", "password2"));

                when(usuarioServiceSelector.getUsuarioService().getAllUsuarios()).thenReturn(usuarios);

                mockMvc.perform(get("/usuario/list"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.size()").value(2))
                                .andExpect(jsonPath("$[0].nombre").value("User One"))
                                .andExpect(jsonPath("$[1].nombre").value("User Two"));
        }

        // Test para actualizar un usuario exitosamente
        @Test
        void testUpdateUsuario() throws Exception {
                UsuarioDto updatedUsuario = new UsuarioDto((long)1, "Updated User", "updated@test.com", "new_password");

                when(usuarioServiceSelector.getUsuarioService().updateUsuario(anyLong(), any(UsuarioDto.class)))
                                .thenReturn(updatedUsuario);

                mockMvc.perform(put("/usuario/update/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"nombre\": \"Updated User\", \"correo\": \"updated@test.com\", \"password\": \"new_password\"}"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nombre").value("Updated User"))
                                .andExpect(jsonPath("$.correo").value("updated@test.com"));
        }

        // Test para fallo en la actualización cuando se proporciona un ID inválido
        @Test
        void testUpdateUsuarioInvalidId() throws Exception {
                UsuarioService usuarioServiceMock = mock(UsuarioService.class);

                when(usuarioServiceSelector.getUsuarioService()).thenReturn(usuarioServiceMock);

                when(usuarioServiceMock.updateUsuario(anyLong(), any(UsuarioDto.class)))
                                .thenThrow(new IllegalArgumentException("Invalid ID"));

                mockMvc.perform(put("/usuario/update/invalid-id")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"nombre\": \"User\", \"correo\": \"user@test.com\", \"password\": \"password\"}"))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testDeleteUsuario() throws Exception {
                UsuarioService usuarioServiceMock = mock(UsuarioService.class);

                when(usuarioServiceSelector.getUsuarioService()).thenReturn(usuarioServiceMock);

                doNothing().when(usuarioServiceMock).deleteUsuario((long)1);

                mockMvc.perform(delete("/usuario/delete/1"))
                                .andExpect(status().isOk());
        }

}
