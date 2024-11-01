package org.douglasalvarado.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "Reserva")
public class ReservaMongo {

    @Id
    private String id;
    private String idUsuario;
    private Integer bookId;
    private String fecha;
    private String descripcion;

}