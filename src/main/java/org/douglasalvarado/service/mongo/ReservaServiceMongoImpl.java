package org.douglasalvarado.service.mongo;

import org.douglasalvarado.dto.ReservaDto;
import org.douglasalvarado.interfaces.ReservaService;
import org.douglasalvarado.model.mongo.ReservaMongo;
import org.douglasalvarado.repository.mongo.ReservaMongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaServiceMongoImpl implements ReservaService {
    private final ReservaMongoRepository reservaRepository;

    public ReservaServiceMongoImpl(ReservaMongoRepository reservaRepository) {
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

    private ReservaMongo toModel(ReservaDto reservaDto) {
        String idString = reservaDto.getId().toString();
        return ReservaMongo.builder()
                .id(idString)
                .idUsuario(reservaDto.getIdUsuario())
                .bookId(reservaDto.getBookId())
                .fecha(reservaDto.getFecha())
                .descripcion(reservaDto.getDescripcion())
                .build();
    }

    private ReservaDto toDto(ReservaMongo reserva) {
        return ReservaDto.builder()
                .idUsuario(reserva.getIdUsuario())
                .bookId(reserva.getBookId())
                .fecha(reserva.getFecha())
                .descripcion(reserva.getDescripcion())
                .build();
    }
}
