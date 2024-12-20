package org.douglasalvarado.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservaDto {

    private Long id;
    private String idUsuario;
    private Integer bookId;
    private String fecha;
    private String descripcion;
}
