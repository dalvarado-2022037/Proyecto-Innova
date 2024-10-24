package org.douglasalvarado.controller;

import org.douglasalvarado.dto.ReservaDto;
import org.douglasalvarado.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reserva")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping("/create")
    public ResponseEntity<ReservaDto> createReserva(@RequestBody ReservaDto reserva) {
        try {
            ReservaDto createdReserva = reservaService.createReserva(reserva);
            return ResponseEntity.ok(createdReserva);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/find-by/{id}")
    public ResponseEntity<ReservaDto> getReserva(@PathVariable String id) {
        Optional<ReservaDto> reserva = Optional.ofNullable(reservaService.getReserva(id));
        return reserva.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReservaDto>> getAllReservas() {
        List<ReservaDto> reservas = reservaService.getAllReservas();
        return ResponseEntity.ok(reservas);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReservaDto> updateReserva(@PathVariable String id, @RequestBody ReservaDto reserva) {
        try {
            ReservaDto updatedReserva = reservaService.updateReserva(id, reserva);
            return ResponseEntity.ok(updatedReserva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable String id) {
        try {
            reservaService.deleteReserva(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
