package org.douglasalvarado.repository.postgres;

import org.douglasalvarado.model.postgres.ReservaPostgres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaPostgresRepository extends JpaRepository<ReservaPostgres, String> {
}
