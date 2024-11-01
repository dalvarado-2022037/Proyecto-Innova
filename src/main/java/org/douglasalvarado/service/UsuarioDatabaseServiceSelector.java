package org.douglasalvarado.service;

import org.douglasalvarado.interfaces.UsuarioService;
import org.douglasalvarado.service.mongo.UsuarioServiceMongoImpl;
import org.douglasalvarado.service.postgres.UsuarioServicePostgresImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDatabaseServiceSelector {
    private final UsuarioServiceMongoImpl usuarioServiceMongo;
    private final UsuarioServicePostgresImpl usuarioServicePostgres;

    @Value("${app.database.type}")
    private String databaseType;

    public UsuarioDatabaseServiceSelector(UsuarioServiceMongoImpl usuarioServiceMongo, UsuarioServicePostgresImpl usuarioServicePostgres) {
        this.usuarioServiceMongo = usuarioServiceMongo;
        this.usuarioServicePostgres = usuarioServicePostgres;
    }

    public UsuarioService getUsuarioService() {
        return databaseType.equalsIgnoreCase("mongo") ? usuarioServiceMongo : usuarioServicePostgres;
    }
}
