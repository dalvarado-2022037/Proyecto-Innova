package org.douglasalvarado.controller;

import org.douglasalvarado.dto.ReservaDto;
import org.douglasalvarado.service.ReservaDatabaseServiceSelector;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/reserva")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaDatabaseServiceSelector serviceSelector;

    @PostMapping("/reservar")
    public ResponseEntity<ReservaDto> createReserva(@RequestBody ReservaDto reservaDto) {
        try {
            ReservaDto createdReserva = serviceSelector.getReservaService().createReserva(reservaDto);
            return ResponseEntity.ok(createdReserva);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/find-by/{id}")
    public ResponseEntity<ReservaDto> getReserva(@PathVariable String id) {
        ReservaDto reserva = serviceSelector.getReservaService().getReserva(id);
        return reserva != null ? ResponseEntity.ok(reserva) : ResponseEntity.status(404).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReservaDto>> getAllReservas() {
        List<ReservaDto> reservas = serviceSelector.getReservaService().getAllReservas();
        return ResponseEntity.ok(reservas);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReservaDto> updateReserva(@PathVariable String id, @RequestBody ReservaDto reservaDto) {
        try {
            ReservaDto updatedReserva = serviceSelector.getReservaService().updateReserva(id, reservaDto);
            return updatedReserva != null ? ResponseEntity.ok(updatedReserva) : ResponseEntity.status(404).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable String id) {
        try {
            serviceSelector.getReservaService().deleteReserva(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
