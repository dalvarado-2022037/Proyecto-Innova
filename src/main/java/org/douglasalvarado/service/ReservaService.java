package org.douglasalvarado.service;

import org.douglasalvarado.dto.ReservaDto;
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

    public ReservaDto createReserva(ReservaDto reserva) {
        return toDto(reservaRepository.save(toModel(reserva)));
    }

    public ReservaDto getReserva(String id) {
        return toDto(reservaRepository.findById(id).orElse(null));
    }

    public List<ReservaDto> getAllReservas() {
        return reservaRepository.findAll().stream()
            .map(this::toDto).toList();
    }

    public ReservaDto updateReserva(String id, ReservaDto reserva) {
        Reserva reservaExistente = reservaRepository.findById(id).orElse(null);
        if (reservaExistente != null) {
            reservaExistente.setFecha(reserva.getFecha());
            reservaExistente.setDescripcion(reserva.getDescripcion());
            return toDto(reservaRepository.save(reservaExistente));
        }
        return null;
    }

    public void deleteReserva(String id) {
        reservaRepository.deleteById(id);
    }

    public Reserva toModel(ReservaDto reservaDto){
        return Reserva.builder()
            .id(reservaDto.getId())
            .idUsuario(reservaDto.getIdUsuario())
            .bookId(reservaDto.getBookId())
            .fecha(reservaDto.getFecha())
            .descripcion(reservaDto.getDescripcion())
            .build();
    }

    public ReservaDto toDto(Reserva reserva){
        return ReservaDto.builder()
            .id(reserva.getId())
            .idUsuario(reserva.getIdUsuario())
            .bookId(reserva.getBookId())
            .fecha(reserva.getFecha())
            .descripcion(reserva.getDescripcion())
            .build();
    }
}
