package org.douglasalvarado.service;

import org.douglasalvarado.interfaces.ReservaService;
import org.douglasalvarado.service.mongo.ReservaServiceMongoImpl;
import org.douglasalvarado.service.postgres.ReservaServicePostgresImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReservaDatabaseServiceSelector {

    private final ReservaServiceMongoImpl reservaServiceMongo;
    private final ReservaServicePostgresImpl reservaServicePostgres;

    @Value("${app.database.type}")
    private String databaseType;

    public ReservaDatabaseServiceSelector(ReservaServiceMongoImpl reservaServiceMongo, ReservaServicePostgresImpl reservaServicePostgres) {
        this.reservaServiceMongo = reservaServiceMongo;
        this.reservaServicePostgres = reservaServicePostgres;
    }

    public ReservaService getReservaService() {
        return databaseType.equalsIgnoreCase("mongo") ? reservaServiceMongo : reservaServicePostgres;
    }
}
