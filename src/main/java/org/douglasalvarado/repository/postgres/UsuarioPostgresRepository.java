package org.douglasalvarado.repository.postgres;

import org.douglasalvarado.model.postgres.UsuarioPostgres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioPostgresRepository extends JpaRepository<UsuarioPostgres, Long> {
    UsuarioPostgres findByCorreo(String correo);
    boolean existsByCorreo(String email);
}