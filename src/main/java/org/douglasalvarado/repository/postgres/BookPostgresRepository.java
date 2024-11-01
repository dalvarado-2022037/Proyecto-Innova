package org.douglasalvarado.repository.postgres;

import org.douglasalvarado.model.postgres.BookPostgres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookPostgresRepository extends JpaRepository<BookPostgres, Integer> {
}
