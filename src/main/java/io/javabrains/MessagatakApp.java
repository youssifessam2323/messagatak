package io.javabrains;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import io.javabrains.messagatak.emaillist.EmailListItem;
import io.javabrains.messagatak.emaillist.EmailListItemKey;
import io.javabrains.messagatak.emaillist.EmailListItemRepository;
import io.javabrains.messagatak.folders.Folder;
import io.javabrains.messagatak.folders.FolderRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@RestController
public class MessagatakApp {

	private final FolderRepository folderRepository;
	private final EmailListItemRepository emailListItemRepository;


	public MessagatakApp(FolderRepository folderRepository, EmailListItemRepository emailListItemRepository) {
		this.folderRepository = folderRepository;
		this.emailListItemRepository = emailListItemRepository;
	}


	public static void main(String[] args) {
		SpringApplication.run(MessagatakApp.class, args);
	}






	@PostConstruct
	public void init(){
		folderRepository.save(new Folder("youssifessam2323", "Inbox", "blue"));
		folderRepository.save(new Folder("youssifessam2323", "Send", "green"));
		folderRepository.save(new Folder("youssifessam2323", "Important", "yellow"));


		for (int i = 0; i < 10; i++) {
			EmailListItemKey key = new EmailListItemKey();

			key.setUserId("youssifessam2323");
			key.setLabel("Inbox");
			key.setTimeUUID(Uuids.timeBased());


			EmailListItem item = new EmailListItem();

			item.setEmailListItemKey(key);
			item.setTo(List.of("youssifessam2323"));
			item.setSubject("Subject " + i);
			item.setUnread(true);

			emailListItemRepository.save(item);
		}
	}
}