package org.douglasalvarado.repository;

import java.util.Optional;

import org.douglasalvarado.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String email);
}
