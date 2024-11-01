package org.douglasalvarado.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@Profile("mongo")
@EnableMongoRepositories(basePackages = "org.douglasalvarado.repository.mongo")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "InnovaDB";
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://douglasalvarado:goku-333@cluster0.pglox.mongodb.net/InnovaDB?retryWrites=true&w=majority&appName=Cluster0");
    }
}
