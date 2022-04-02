package io.javabrains.controller;


import com.datastax.oss.driver.api.core.uuid.Uuids;
import io.javabrains.messagatak.emaillist.EmailListItem;
import io.javabrains.messagatak.emaillist.EmailListItemRepository;
import io.javabrains.messagatak.folders.Folder;
import io.javabrains.messagatak.folders.FolderRepository;
import io.javabrains.messagatak.folders.FolderService;
import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class InboxController {

    Logger logger = LoggerFactory.getLogger(InboxController.class);

    private final FolderRepository folderRepository;

    private final FolderService folderService;
    private final EmailListItemRepository emailListItemRepository;


    public InboxController(FolderRepository folderRepository, FolderService folderService, EmailListItemRepository emailListItemRepository) {
        this.folderRepository = folderRepository;
        this.folderService = folderService;
        this.emailListItemRepository = emailListItemRepository;
    }

    @GetMapping(path = {"/", "/home"})
    public String homePage(
            @AuthenticationPrincipal OAuth2User principal,
            Model model,
            @RequestParam(required = false) String folder
    ){
        if(principal == null || !StringUtils.hasText(principal.getAttribute("login"))){
            return "index";
        }

        logger.info("USER INFO => " + principal);

        String userId = principal.getAttribute("login");

        logger.info("user id "  +  userId);

        List<Folder> folders = folderRepository.findAllByUserId(userId);

        List<Folder> defaultFolders = folderService.getDefaultFolders(userId);

        model.addAttribute("folders", folders);

        model.addAttribute("defaultFolders", defaultFolders);

        model.addAttribute("name", principal.getAttribute("name"));
        //fetch messages
        String label = !StringUtils.hasText(folder) ? "Inbox" : folder;


        List<EmailListItem> emailList = emailListItemRepository.findAllByKey_UserIdAndKey_Label(userId, label);


        emailList
                .forEach(email -> {
                    PrettyTime p = new PrettyTime();
                    UUID  timeUUID = email.getEmailListItemKey().getTimeUUID();
                    Date emailDateTime = new Date(Uuids.unixTimestamp(timeUUID));
                    email.setAgoTimeString(p.format(emailDateTime));
                });
        model.addAttribute("emailList", emailList);
        model.addAttribute("folderName", label);

        return "inbox-page";
    }
}
