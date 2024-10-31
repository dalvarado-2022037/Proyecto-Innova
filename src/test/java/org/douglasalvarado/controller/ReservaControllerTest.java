package org.douglasalvarado.controller;

import org.douglasalvarado.dto.BookDto;
import org.douglasalvarado.dto.ReservaDto;
import org.douglasalvarado.service.BookService;
import org.douglasalvarado.service.ReservaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ReservaControllerTest {

    @Mock
    private ReservaService reservaService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private ReservaController reservaController;

    private MockMvc mockMvc;

    public ReservaControllerTest() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservaController).build();
    }

    // Obtener todas las reservas correcto
    @Test
    void testGetAllReservas() throws Exception {
        List<ReservaDto> reservas = Arrays.asList(new ReservaDto(), new ReservaDto());
        when(reservaService.getAllReservas()).thenReturn(reservas);

        mockMvc.perform(get("/reserva/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // Crear una reserva correcto
    @Test
    void testCreateReserva() throws Exception {
        ReservaDto reserva = ReservaDto.builder()
            .id("1")
            .idUsuario("1")
            .bookId(1)
            .fecha("2023-10-23")
            .descripcion("Test")
            .build();

        BookDto bookDto = new BookDto();
        
        when(bookService.reservarBook(any(Integer.class), anyBoolean())).thenReturn(bookDto);
        when(reservaService.createReserva(any(ReservaDto.class))).thenReturn(reserva);

        mockMvc.perform(post("/reserva/reservar")  // Asegúrate que esta sea la URL correcta
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"id\": \"1\", \"idUsuario\": \"1\", \"bookId\": 1, \"fecha\": \"2023-10-23\", \"descripcion\": \"Test\"}"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // Crear una reserva con error Internal Server Error
    @Test
    void testCreateReservaInternalServerError() throws Exception {
        when(reservaService.createReserva(any(ReservaDto.class))).thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/reserva/reservar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUsuario\": \"1\", \"fecha\": \"2023-10-23\", \"descripcion\": \"Test\"}"))
                .andExpect(status().isInternalServerError());
    }

    // Test de buscar por ID correcto
    @Test
    void testGetReservaById() throws Exception {
        ReservaDto reserva = new ReservaDto();
        when(reservaService.getReserva(anyString())).thenReturn(reserva);

        mockMvc.perform(get("/reserva/find-by/123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // Obtener reserva por id Not Found
    @Test
    void testGetReservaByIdNotFound() throws Exception {
        when(reservaService.getReserva(anyString())).thenReturn(null);

        mockMvc.perform(get("/reserva/find-by/123"))
                .andExpect(status().isNotFound());
    }

    // Actualizar reserva correcto
    @Test
    void testUpdateReserva() throws Exception {
        ReservaDto updatedReserva = new ReservaDto();
        when(reservaService.updateReserva(anyString(), any(ReservaDto.class))).thenReturn(updatedReserva);

        mockMvc.perform(put("/reserva/update/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUsuario\": \"1\", \"fecha\": \"2023-10-23\", \"descripcion\": \"Updated Test\"}"))
                .andExpect(status().isOk());
    }

    // Actualizar reserva con BadRequest
    @Test
    void testUpdateReservaBadRequest() throws Exception {
        when(reservaService.updateReserva(anyString(), any(ReservaDto.class)))
                .thenThrow(new IllegalArgumentException());

        mockMvc.perform(put("/reserva/update/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUsuario\": \"1\", \"fecha\": \"2023-10-23\", \"descripcion\": \"Updated Test\"}"))
                .andExpect(status().isBadRequest());
    }

    // Actualizar reserva con Internal Server Error
    @Test
    void testUpdateReservaInternalServerError() throws Exception {
        when(reservaService.updateReserva(anyString(), any(ReservaDto.class)))
                .thenThrow(new RuntimeException());

        mockMvc.perform(put("/reserva/update/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUsuario\": \"1\", \"fecha\": \"2023-10-23\", \"descripcion\": \"Updated Test\"}"))
                .andExpect(status().isInternalServerError());
    }

    // Eliminar reserva correcto
    @Test
    void testDeleteReserva() throws Exception {
        mockMvc.perform(delete("/reserva/delete/123"))
                .andExpect(status().isOk());
    }

    // Eliminar una reserva Internal Server Error
    @Test
    void testDeleteReservaInternalServerError() throws Exception {
        doThrow(new IllegalArgumentException("Invalid ID")).when(reservaService).deleteReserva(anyString());

        mockMvc.perform(delete("/reserva/delete/123"))
                .andExpect(status().isInternalServerError());
    }

    // Devolver un libro correctamente
    @Test
    void testDevolver() throws Exception {
        BookDto bookDto = new BookDto();
        when(bookService.reservarBook(anyInt(), eq(false))).thenReturn(bookDto);

        mockMvc.perform(post("/reserva/devolver")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"bookId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // Devolver un libro con error de servidor
    @Test
    void testDevolverServerError() throws Exception {
        when(bookService.reservarBook(anyInt(), eq(false))).thenThrow(new RuntimeException("Error al devolver el libro"));

        mockMvc.perform(post("/reserva/devolver")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"bookId\": 1}"))
                .andExpect(status().isInternalServerError());
    }
}
