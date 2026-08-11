package com.notification.api.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MongoConfig {

    private final ApplicationProperties props;

    @Bean
    MongoClient mongoClient() {

        ConnectionString connStr = new ConnectionString(props.getMongoConnectionURI());

        MongoClientSettings build = MongoClientSettings.builder()
                .applyConnectionString(connStr)
                .applyToSocketSettings(
                        builder -> builder.connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS))
                .applyToClusterSettings(builder -> builder.serverSelectionTimeout(5, TimeUnit.SECONDS))
                .build();

        return MongoClients.create(build);
    }

}