package io.javabrains.messagatak.folders;


import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FolderService {
    public List<Folder> getDefaultFolders(String userId){
        return Arrays.asList(
                new Folder(userId,"Inbox", "white"),
                new Folder(userId,"Sent", "green"),
                new Folder(userId,"Important", "red")
        );
    }
}
