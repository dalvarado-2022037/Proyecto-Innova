package org.douglasalvarado.service.postgres;

import org.douglasalvarado.dto.ReservaDto;
import org.douglasalvarado.interfaces.ReservaService;
import org.douglasalvarado.model.postgres.ReservaPostgres;
import org.douglasalvarado.repository.postgres.ReservaPostgresRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaServicePostgresImpl implements ReservaService {
    private final ReservaPostgresRepository reservaRepository;

    public ReservaServicePostgresImpl(ReservaPostgresRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public ReservaDto createReserva(ReservaDto reservaDto) {
        return toDto(reservaRepository.save(toModel(reservaDto)));
    }

    @Override
    public List<ReservaDto> getAllReservas() {
        return reservaRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public ReservaDto getReserva(String id) {
        return reservaRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public ReservaDto updateReserva(String id, ReservaDto reservaDto) {
        return reservaRepository.findById(id)
                .map(existing -> {
                    existing.setIdUsuario(reservaDto.getIdUsuario());
                    existing.setBookId(reservaDto.getBookId());
                    existing.setFecha(reservaDto.getFecha());
                    existing.setDescripcion(reservaDto.getDescripcion());
                    return toDto(reservaRepository.save(existing));
                }).orElse(null);
    }

    @Override
    public void deleteReserva(String id) {
        reservaRepository.deleteById(id);
    }

    private ReservaPostgres toModel(ReservaDto reservaDto) {
        return ReservaPostgres.builder()
                .id(reservaDto.getId())
                .idUsuario(reservaDto.getIdUsuario())
                .bookId(reservaDto.getBookId())
                .fecha(reservaDto.getFecha())
                .descripcion(reservaDto.getDescripcion())
                .build();
    }

    private ReservaDto toDto(ReservaPostgres reserva) {
        return ReservaDto.builder()
                .id(reserva.getId())
                .idUsuario(reserva.getIdUsuario())
                .bookId(reserva.getBookId())
                .fecha(reserva.getFecha())
                .descripcion(reserva.getDescripcion())
                .build();
    }
}
