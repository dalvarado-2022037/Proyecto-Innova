package org.douglasalvarado.config;

import org.douglasalvarado.dto.UsuarioDto;
import org.douglasalvarado.model.mongo.UsuarioMongo;
import org.douglasalvarado.service.UsuarioDatabaseServiceSelector;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UsuarioDatabaseServiceSelector serviceSelector;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        UsuarioDto usuario = serviceSelector.getUsuarioService().findByCorreo(correo);
        if (usuario == null) {
            throw new UsernameNotFoundException("User not found with email: " + correo);
        }

        return User.withUsername(usuario.getCorreo())
                .password(usuario.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }
}
