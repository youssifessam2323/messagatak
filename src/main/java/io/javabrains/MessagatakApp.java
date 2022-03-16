package io.javabrains;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;

@SpringBootApplication
@RestController
public class MessagatakApp {

	public static void main(String[] args) {
		SpringApplication.run(MessagatakApp.class, args);
	}


	/**
	 * This is important in order to make spring boot use the secure bundle file to connect to DB
	 */
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties properties){
		Path bundle = properties.getSecureConnectBundle().toPath();

		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}
}