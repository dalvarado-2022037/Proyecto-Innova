package org.douglasalvarado.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {
    private Long id;
    private String nombre;
    private String correo;
    private String password;
    public Object map(Object object) {
        throw new UnsupportedOperationException("Unimplemented method 'map'");
    }
}
