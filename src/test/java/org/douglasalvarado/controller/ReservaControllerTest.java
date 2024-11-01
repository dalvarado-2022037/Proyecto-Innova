package org.douglasalvarado.controller;

import org.douglasalvarado.dto.ReservaDto;
import org.douglasalvarado.service.ReservaDatabaseServiceSelector;
import org.douglasalvarado.interfaces.ReservaService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ReservaControllerTest {

    @Mock
    private ReservaDatabaseServiceSelector serviceSelector;

    @InjectMocks
    private ReservaController reservaController;

    @Mock
    private ReservaService reservaService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        when(serviceSelector.getReservaService()).thenReturn(reservaService);
        mockMvc = MockMvcBuilders.standaloneSetup(reservaController).build();
    }

    // Test para crear una reserva exitosamente
    @Test
    void createReservaSuccessfully() throws Exception {
        ReservaDto reservaDto = new ReservaDto((long)1, "123", 456, "2024-10-31", "Reserva de prueba");
        when(reservaService.createReserva(any(ReservaDto.class))).thenReturn(reservaDto);

        mockMvc.perform(post("/reserva/reservar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUsuario\": \"123\", \"bookId\": 456, \"fecha\": \"2024-10-31\", \"descripcion\": \"Reserva de prueba\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value("123"))
                .andExpect(jsonPath("$.bookId").value(456));
    }

    // Test para obtener una reserva por ID exitosamente
    @Test
    void getReservaByIdSuccessfully() throws Exception {
        ReservaDto reservaDto = new ReservaDto((long)1, "123", 456, "2024-10-31", "Reserva de prueba");
        when(reservaService.getReserva("1")).thenReturn(reservaDto);

        mockMvc.perform(get("/reserva/find-by/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value("123"));
    }

    // Test para manejar cuando una reserva no es encontrada
    @Test
    void getReservaByIdNotFound() throws Exception {
        when(reservaService.getReserva(anyString())).thenReturn(null);

        mockMvc.perform(get("/reserva/find-by/1"))
                .andExpect(status().isNotFound());
    }

    // Test para obtener todas las reservas
    @Test
    void getAllReservasSuccessfully() throws Exception {
        List<ReservaDto> reservas = List.of(new ReservaDto((long)1, "123", 456, "2024-10-31", "Reserva de prueba"));
        when(reservaService.getAllReservas()).thenReturn(reservas);

        mockMvc.perform(get("/reserva/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idUsuario").value("123"));
    }

    // Test para actualizar una reserva exitosamente
    @Test
    void updateReservaSuccessfully() throws Exception {
        ReservaDto updatedReserva = new ReservaDto((long)1, "123", 456, "2024-10-31", "Reserva actualizada");
        when(reservaService.updateReserva(anyString(), any(ReservaDto.class))).thenReturn(updatedReserva);

        mockMvc.perform(put("/reserva/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUsuario\": \"123\", \"bookId\": 456, \"fecha\": \"2024-10-31\", \"descripcion\": \"Reserva actualizada\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Reserva actualizada"));
    }

    // Test para manejar cuando una reserva a actualizar no es encontrada
    @Test
    void updateReservaNotFound() throws Exception {
        when(reservaService.updateReserva(anyString(), any(ReservaDto.class))).thenReturn(null);

        mockMvc.perform(put("/reserva/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUsuario\": \"123\", \"bookId\": 456, \"fecha\": \"2024-10-31\", \"descripcion\": \"Reserva actualizada\"}"))
                .andExpect(status().isNotFound());
    }

    // Test para eliminar una reserva exitosamente
    @Test
    void deleteReservaSuccessfully() throws Exception {
        doNothing().when(reservaService).deleteReserva("1");

        mockMvc.perform(delete("/reserva/delete/1"))
                .andExpect(status().isOk());
    }
}
