package com.notification.api.bootstrap;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoClient;
import com.notification.api.config.ApplicationProperties;
import com.notification.api.exception.ValidationException;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConnectionValidator {

    private final RedisConnectionFactory redisConnectionFactory;
    private final MongoClient mongoClient;
    private final ApplicationProperties props;

    @PostConstruct
    public void init() {
        testRedisConn();
        testMongoConn();
        //testkafkaConn();
    }

    private void testkafkaConn() {

        try (AdminClient client = AdminClient.create(Map.of("bootstrap.servers", props.getBootstrapKafkaServers()))) {

            client.listTopics().names().get(5, TimeUnit.SECONDS);

        } catch (Exception e) {
            log.error("Error while pinging Kafka connection", e);
            throw new ValidationException("Error while connecting to kafka", HttpStatus.BAD_REQUEST.value());
        }
    }

    private void testMongoConn() {
        try {
            mongoClient.listDatabases().first();

        } catch (Exception e) {
            log.error("Error while pinging MongoDB connection", e);
            throw e;
        }
    }

    private void testRedisConn() {
        try {
            redisConnectionFactory.getConnection().ping();

        } catch (Exception e) {
            log.error("Error while pinging redis connection", e);
            throw e;
        }
    }

}