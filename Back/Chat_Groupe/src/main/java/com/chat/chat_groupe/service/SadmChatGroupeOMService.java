package com.chat.chat_groupe.service;

import com.chat.chat_groupe.jpa.SadmChatGroupeOM;
import com.chat.chat_groupe.models.SadmChatGroupeResponseOM;

import java.util.List;

public interface SadmChatGroupeOMService {

    SadmChatGroupeOM saveChatGroupe(SadmChatGroupeOM chatGroupe);
    SadmChatGroupeResponseOM getChatGroupeById(Long id,String jwtToken);
    List<SadmChatGroupeResponseOM> getAllChat(String jwtToken);
    SadmChatGroupeResponseOM findByTitre(String titre,String jwtToken);
    void deleteChatGroupe(Long id);
    List<SadmChatGroupeResponseOM> getAllChatbyDocteurId(Long id,String jwtToken);
    String Archiver(Long id);
    List<SadmChatGroupeResponseOM> getAllChatbyDocteurIdArchiver(Long id,String jwtToken);


}
