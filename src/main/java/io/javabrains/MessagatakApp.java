package io.javabrains;

import com.datastax.oss.driver.internal.core.session.DefaultSession;
import io.javabrains.messagatak.folders.Folder;
import io.javabrains.messagatak.folders.FolderRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.nio.file.Path;

@SpringBootApplication
@RestController
public class MessagatakApp {

	private final FolderRepository folderRepository;

	public MessagatakApp(FolderRepository folderRepository) {
		this.folderRepository = folderRepository;
	}


	public static void main(String[] args) {
		SpringApplication.run(MessagatakApp.class, args);
	}






	@PostConstruct
	public void init(){
		folderRepository.save(new Folder("youssifessam2323", "Inbox", "blue"));
		folderRepository.save(new Folder("youssifessam2323", "Send", "green"));
		folderRepository.save(new Folder("youssifessam2323", "Important", "yellow"));

	}
}