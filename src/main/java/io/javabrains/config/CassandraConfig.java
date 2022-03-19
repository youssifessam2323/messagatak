package io.javabrains.config;


import io.javabrains.DataStaxAstraProperties;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class CassandraConfig {

    /**
     * This is important in order to make spring boot use the secure bundle file to connect to DB
     */
    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties properties){
        Path bundle = properties.getSecureConnectBundle().toPath();

        return builder -> builder.withCloudSecureConnectBundle(bundle);
    }

}
