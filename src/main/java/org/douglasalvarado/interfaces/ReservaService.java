package org.douglasalvarado.interfaces;

import org.douglasalvarado.dto.ReservaDto;
import java.util.List;

public interface ReservaService {
    ReservaDto createReserva(ReservaDto reservaDto);
    List<ReservaDto> getAllReservas();
    ReservaDto getReserva(String id);
    ReservaDto updateReserva(String id, ReservaDto reservaDto);
    void deleteReserva(String id);
}
