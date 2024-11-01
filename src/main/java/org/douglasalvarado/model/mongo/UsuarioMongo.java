package org.douglasalvarado.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "Usuario")
public class UsuarioMongo {

    @Id
    private String id;
    private String nombre;
    private String correo;
    private String password;
}
