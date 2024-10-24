package org.douglasalvarado.service;

import org.douglasalvarado.dto.ReservaDto;
import org.douglasalvarado.model.Reserva;
import org.douglasalvarado.repository.ReservaRepository;
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

class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    private Reserva reserva;
    private ReservaDto reservaDto;

    //Configuraciones para un dto y model 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reserva = Reserva.builder()
                .id("1")
                .idUsuario("123")
                .fecha("01/10/2024")
                .descripcion("Reserva para evento")
                .build();

        reservaDto = ReservaDto.builder()
                .id("1")
                .idUsuario("123")
                .fecha("01/10/2024")
                .descripcion("Reserva para evento")
                .build();
    }

    //Crear una reserva
    @Test
    void testCreateReserva() {
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        ReservaDto createdReserva = reservaService.createReserva(reservaDto);

        assertNotNull(createdReserva);
        assertEquals(reservaDto.getId(), createdReserva.getId());
        assertEquals(reservaDto.getDescripcion(), createdReserva.getDescripcion());
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    //Buscar una reserva
    @Test
    void testGetReserva() {
        when(reservaRepository.findById("1")).thenReturn(Optional.of(reserva));

        ReservaDto foundReserva = reservaService.getReserva("1");

        assertNotNull(foundReserva);
        assertEquals(reservaDto.getId(), foundReserva.getId());
        verify(reservaRepository, times(1)).findById("1");
    }

    //Actualizar una reserva
    @Test
    void testUpdateReserva() {
        when(reservaRepository.findById("1")).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        reservaDto.setDescripcion("Reserva modificada");
        ReservaDto updatedReserva = reservaService.updateReserva("1", reservaDto);

        assertNotNull(updatedReserva);
        assertEquals("Reserva modificada", updatedReserva.getDescripcion());
        verify(reservaRepository, times(1)).findById("1");
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    //Actualizar una reserva que no existe
    @Test
    void testUpdateReservaReturnsNull() {
        when(reservaRepository.findById("1")).thenReturn(Optional.empty());

        ReservaDto result = reservaService.updateReserva("1", reservaDto);

        assertNull(result);

        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    //Eliminar una reserva
    @Test
    void testDeleteReserva() {
        doNothing().when(reservaRepository).deleteById("1");

        reservaService.deleteReserva("1");

        verify(reservaRepository, times(1)).deleteById("1");
    }

    //Obtener todas las reservas
    @Test
    void testGetAllReservas() {
        when(reservaRepository.findAll()).thenReturn(List.of(reserva));

        List<ReservaDto> allReservas = reservaService.getAllReservas();

        assertNotNull(allReservas);
        assertEquals(1, allReservas.size());
        verify(reservaRepository, times(1)).findAll();
    }
}
