package org.douglasalvarado.repository.mongo;

import org.douglasalvarado.model.mongo.ReservaMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaMongoRepository extends MongoRepository<ReservaMongo, String> {
}
