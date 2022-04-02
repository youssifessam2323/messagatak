package io.javabrains.config;


import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import io.javabrains.DataStaxAstraProperties;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.autoconfigure.cassandra.DriverConfigLoaderBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.time.Duration;

@Configuration
public class CassandraConfig {

    /**
     * This is important in order to make spring boot use the secure bundle file to connect to DB
     */
    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties properties){
        Path bundle = properties.getSecureConnectBundle().toPath();

        return builder -> {
                    builder .withCloudSecureConnectBundle(bundle)
                            .withConfigLoader(DriverConfigLoader.programmaticBuilder()
                            // Resolves the timeout query 'SELECT * FROM system_schema.tables' timed out after PT2S
                            .withDuration(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT, Duration.ofMillis(60000))
                            .withDuration(DefaultDriverOption.CONNECTION_INIT_QUERY_TIMEOUT, Duration.ofMillis(60000))
                            .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofMillis(150000))
                            .build());
        };
    }
}
