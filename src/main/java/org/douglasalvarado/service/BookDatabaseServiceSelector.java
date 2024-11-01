package org.douglasalvarado.service;

import org.douglasalvarado.interfaces.BookService;
import org.douglasalvarado.service.mongo.BookServiceMongoImpl;
import org.douglasalvarado.service.postgres.BookServicePostgresImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BookDatabaseServiceSelector {

    private final BookServiceMongoImpl bookServiceMongo;
    private final BookServicePostgresImpl bookServicePostgres;

    @Value("${app.database.type}")
    private String databaseType;

    public BookDatabaseServiceSelector(BookServiceMongoImpl bookServiceMongo, BookServicePostgresImpl bookServicePostgres) {
        this.bookServiceMongo = bookServiceMongo;
        this.bookServicePostgres = bookServicePostgres;
    }

    public BookService getBookService() {
        return databaseType.equalsIgnoreCase("mongo") ? bookServiceMongo : bookServicePostgres;
    }
}
