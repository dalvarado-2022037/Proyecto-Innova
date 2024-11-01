package org.douglasalvarado.repository.mongo;

import org.douglasalvarado.model.mongo.BookMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMongoRepository extends MongoRepository<BookMongo, String> {
}
