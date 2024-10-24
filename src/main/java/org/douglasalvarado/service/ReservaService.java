package org.douglasalvarado.service;

import org.douglasalvarado.model.Reserva;
import org.douglasalvarado.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva createReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public Reserva getReserva(String id) {
        return reservaRepository.findById(id).orElse(null);
    }

    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }

    public Reserva updateReserva(String id, Reserva reserva) {
        Reserva reservaExistente = reservaRepository.findById(id).orElse(null);
        if (reservaExistente != null) {
            reservaExistente.setFecha(reserva.getFecha());
            reservaExistente.setDescripcion(reserva.getDescripcion());
            return reservaRepository.save(reservaExistente);
        }
        return null;
    }

    public void deleteReserva(String id) {
        reservaRepository.deleteById(id);
    }
}
