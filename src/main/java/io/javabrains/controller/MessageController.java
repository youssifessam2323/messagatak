package io.javabrains.controller;


import io.javabrains.messagatak.email.Email;
import io.javabrains.messagatak.email.EmailRepository;
import io.javabrains.messagatak.folders.Folder;
import io.javabrains.messagatak.folders.FolderRepository;
import io.javabrains.messagatak.folders.FolderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class MessageController {

    private final FolderRepository folderRepository;

    private final FolderService folderService;
    private final EmailRepository emailRepository;



    public MessageController(FolderRepository folderRepository, FolderService folderService, EmailRepository emailRepository) {
        this.folderRepository = folderRepository;
        this.folderService = folderService;
        this.emailRepository = emailRepository;
    }

    @GetMapping("/messages/{id}")
    public String messageView(
            @PathVariable("id") String id,
            Model model,
            @AuthenticationPrincipal OAuth2User principal
    ){

        if(principal == null || !StringUtils.hasText("login")){
            return "index";
        }

        String loginId = principal.getAttribute("login");

        List<Folder> folders = folderRepository.findAllByUserId(loginId);

        List<Folder> defaultFolders = folderService.getDefaultFolders(loginId);

        model.addAttribute("folders", folders);

        model.addAttribute("defaultFolders", folders);

        model.addAttribute("name", principal.getAttribute("name"));


        Optional<Email> optionalEmail = emailRepository.findById(UUID.fromString(id));

        if(!optionalEmail.isPresent()){
             model.addAttribute("messageNotFound", true);
             return "message-view";
        }
        model.addAttribute("messageNotFound", false);
        Email email = optionalEmail.get();
        model.addAttribute("email",email) ;

        String toIds = String.join(",", email.getTo());


        System.out.println(toIds);

        model.addAttribute("toIds",toIds) ;

        return "message-view";
    }

}
