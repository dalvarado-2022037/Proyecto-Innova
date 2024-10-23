package org.douglasalvarado.controller;

import org.douglasalvarado.model.Reserva;
import org.douglasalvarado.service.ReservaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/create")
    public Reserva createReserva(@RequestBody Reserva reserva) {
        return reservaService.createReserva(reserva);
    }

    @GetMapping("/find-by/{id}")
    public Reserva getReserva(@PathVariable Long id) {
        return reservaService.getReserva(id);
    }

    @GetMapping("/list")
    public List<Reserva> getAllReservas() {
        return reservaService.getAllReservas();
    }

    @PutMapping("/update/{id}")
    public Reserva updateReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        return reservaService.updateReserva(id, reserva);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
    }
}
