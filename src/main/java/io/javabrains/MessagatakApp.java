package io.javabrains;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import io.javabrains.messagatak.email.Email;
import io.javabrains.messagatak.email.EmailRepository;
import io.javabrains.messagatak.emaillist.EmailListItem;
import io.javabrains.messagatak.emaillist.EmailListItemKey;
import io.javabrains.messagatak.emaillist.EmailListItemRepository;
import io.javabrains.messagatak.folders.Folder;
import io.javabrains.messagatak.folders.FolderRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@RestController
public class MessagatakApp {

	private final FolderRepository folderRepository;
	private final EmailListItemRepository emailListItemRepository;
	private final EmailRepository emailRepository;


	public MessagatakApp(FolderRepository folderRepository, EmailListItemRepository emailListItemRepository, EmailRepository emailRepository) {
		this.folderRepository = folderRepository;
		this.emailListItemRepository = emailListItemRepository;
		this.emailRepository = emailRepository;
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
			item.setFrom("youssifessam2323");
			item.setTo(List.of("koushikkothagal"));
			item.setSubject("Subject " + i);
			item.setUnread(true);

			emailListItemRepository.save(item);

			Email email  = new Email();

			email.setId(item.getEmailListItemKey().getTimeUUID());
			email.setBody("Body " + i);
			email.setSubject(item.getSubject());
			email.setTo(item.getTo());
			email.setFrom("youssifessam2323");

			emailRepository.save(email);
		}
	}
}