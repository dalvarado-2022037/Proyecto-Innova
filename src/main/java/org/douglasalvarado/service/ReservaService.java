package org.douglasalvarado.service;

import org.douglasalvarado.model.Reserva;
import org.douglasalvarado.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva createReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public Reserva getReserva(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }

    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }

    public Reserva updateReserva(Long id, Reserva reserva) {
        Reserva reservaExistente = reservaRepository.findById(id).orElse(null);
        if (reservaExistente != null) {
            reservaExistente.setFecha(reserva.getFecha());
            reservaExistente.setDescripcion(reserva.getDescripcion());
            return reservaRepository.save(reservaExistente);
        }
        return null;
    }

    public void deleteReserva(Long id) {
        reservaRepository.deleteById(id);
    }
}
