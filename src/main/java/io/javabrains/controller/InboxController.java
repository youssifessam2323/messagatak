package io.javabrains.controller;


import io.javabrains.messagatak.folders.Folder;
import io.javabrains.messagatak.folders.FolderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.IntStream;

@Controller
public class InboxController {

    Logger logger = LoggerFactory.getLogger(InboxController.class);

    private final FolderRepository folderRepository;

    public InboxController(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    @GetMapping(path = {"/", "/home"})
    public String homePage(@AuthenticationPrincipal OAuth2User principal, Model model){
        if(principal == null || !StringUtils.hasText(principal.getAttribute("login"))){
            return "index";
        }

        logger.info("USER INFO => " + principal);

        String userId = principal.getAttribute("login");

        logger.info("user id "  +  userId);

        List<Folder> folders = folderRepository.findAllByUserId(userId);

        model.addAttribute("folders", folders);
        return "inbox-page";
    }
}
