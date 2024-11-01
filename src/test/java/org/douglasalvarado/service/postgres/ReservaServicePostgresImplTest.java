package org.douglasalvarado.service.postgres;

import org.douglasalvarado.dto.ReservaDto;
import org.douglasalvarado.model.postgres.ReservaPostgres;
import org.douglasalvarado.repository.postgres.ReservaPostgresRepository;
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

class ReservaServicePostgresImplTest {

    @Mock
    private ReservaPostgresRepository reservaRepository;

    @InjectMocks
    private ReservaServicePostgresImpl reservaService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test para crear una reserva exitosamente
    @Test
    void createReservaSuccessfully() {
        ReservaPostgres reserva = new ReservaPostgres((long)1, "User1", 1, "2024-10-31", "Descripción");
        ReservaDto reservaDto = new ReservaDto((long)1, "User1", 1, "2024-10-31", "Descripción");
        when(reservaRepository.save(any(ReservaPostgres.class))).thenReturn(reserva);

        ReservaDto result = reservaService.createReserva(reservaDto);

        assertNotNull(result);
        assertEquals("User1", result.getIdUsuario());
    }

    // Test para obtener todas las reservas
    @Test
    void getAllReservas() {
        List<ReservaPostgres> reservas = List.of(new ReservaPostgres((long)1, "User1", 1, "2024-10-31", "Descripción"));
        when(reservaRepository.findAll()).thenReturn(reservas);

        List<ReservaDto> result = reservaService.getAllReservas();

        assertEquals(1, result.size());
        assertEquals("User1", result.get(0).getIdUsuario());
    }

    // Test para obtener una reserva por ID exitosamente
    @Test
    void getReservaByIdSuccessfully() {
        ReservaPostgres reserva = new ReservaPostgres((long)1, "User1", 1, "2024-10-31", "Descripción");
        when(reservaRepository.findById("1")).thenReturn(Optional.of(reserva));

        ReservaDto result = reservaService.getReserva("1");

        assertNotNull(result);
        assertEquals("User1", result.getIdUsuario());
    }

    // Test para manejar cuando una reserva no es encontrada por ID
    @Test
    void getReservaByIdNotFound() {
        when(reservaRepository.findById("1")).thenReturn(Optional.empty());

        ReservaDto result = reservaService.getReserva("1");

        assertNull(result);
    }

    // Test para actualizar una reserva exitosamente
    @Test
    void updateReservaSuccessfully() {
        ReservaPostgres reserva = new ReservaPostgres((long)1, "User1", 1, "2024-10-31", "Descripción");
        when(reservaRepository.findById("1")).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(ReservaPostgres.class))).thenReturn(reserva);

        ReservaDto reservaDto = new ReservaDto((long)1, "User1 Updated", 1, "2024-10-31", "Descripción actualizada");
        ReservaDto result = reservaService.updateReserva("1", reservaDto);

        assertNotNull(result);
        assertEquals("User1 Updated", result.getIdUsuario());
    }

    // Test para manejar cuando una reserva a actualizar no es encontrada
    @Test
    void updateReservaNotFound() {
        when(reservaRepository.findById("1")).thenReturn(Optional.empty());

        ReservaDto result = reservaService.updateReserva("1", new ReservaDto());

        assertNull(result);
    }

    // Test para eliminar una reserva exitosamente
    @Test
    void deleteReservaSuccessfully() {
        doNothing().when(reservaRepository).deleteById("1");

        reservaService.deleteReserva("1");

        verify(reservaRepository, times(1)).deleteById("1");
    }
}
