package org.douglasalvarado.repository.mongo;

import org.douglasalvarado.model.mongo.UsuarioMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioMongoRepository extends MongoRepository<UsuarioMongo, String> {
    UsuarioMongo findByCorreo(String correo);
    boolean existsByCorreo(String email);
}
