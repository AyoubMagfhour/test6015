package com.chat.chat_groupe.serviceimpl;

import com.chat.chat_groupe.jpa.Docteur;
import com.chat.chat_groupe.jpa.SadmChatGroupeOM;
import com.chat.chat_groupe.models.SadmChatGroupeResponseOM;
import com.chat.chat_groupe.repository.SadmChatGroupeOMRepository;
import com.chat.chat_groupe.service.SadmChatGroupeOMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class SadmChatGroupeOMServiceImpl implements SadmChatGroupeOMService {

    @Autowired
    private SadmChatGroupeOMRepository chatGroupeRepository;

    @Autowired
    private RestTemplate restTemplate;
    private final String URL="http://localhost:8888/SERVICE-USER";

    @Override
    public SadmChatGroupeOM saveChatGroupe(SadmChatGroupeOM chatGroupe) {
        // Sauvegarde d'un groupe de chat
        return chatGroupeRepository.save(chatGroupe);
    }

    @Override
    public SadmChatGroupeResponseOM getChatGroupeById(Long id, String jwtToken) {
        // Récupération d'un groupe de chat par son identifiant
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        SadmChatGroupeOM chatgroupe = chatGroupeRepository.findById(id).orElse(null);
        if (chatgroupe == null) {
            return null; // or handle the case where the chat group is not found
        }

        ResponseEntity<Docteur> response = restTemplate.exchange(
                this.URL + "/docteurs/" + chatgroupe.getPlgcSFKDocteur(),
                HttpMethod.GET,
                entity,
                Docteur.class
        );
        Docteur docteur = response.getBody();

        return SadmChatGroupeResponseOM.builder()
                .plgcSpkchatGroupe(chatgroupe.getPlgcSpkchatGroupe())
                .plgcCtitre(chatgroupe.getPlgcCtitre())
                .docteur(docteur)
                .plgcXdateCreation(chatgroupe.getPlgcXdateCreation())
                .plgcBarchive(chatgroupe.getPlgcBarchive())
                .comCwhoCreate(chatgroupe.getComCwhoCreate())
                .comXwhenCreate(chatgroupe.getComXwhenCreate())
                .comCwhoUpdate(chatgroupe.getComCwhoUpdate())
                .comXwhenUpdate(chatgroupe.getComXwhenUpdate())
                .comSdesactive(chatgroupe.getComSdesactive())
                .build();
    }


    @Override
    public List<SadmChatGroupeResponseOM> getAllChat(String jwtToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<SadmChatGroupeOM> chatgroupe = chatGroupeRepository.findAll();
        ResponseEntity<Docteur[]> response = restTemplate.exchange(
                this.URL + "/docteurs/all",
                HttpMethod.GET,
                entity,
                Docteur[].class
        );
        Docteur[] docteur = response.getBody();
        return chatgroupe.stream().map((SadmChatGroupeOM chatgroup) ->
                mapToCarResponse(chatgroup, docteur)).toList();
    }



    @Override
    public SadmChatGroupeResponseOM findByTitre(String titre,String jwtToken) {
        // Recherche d'un groupe de chat par son titre
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        SadmChatGroupeOM chatgroupe = chatGroupeRepository.findByPlgcCtitre(titre);
        ResponseEntity<Docteur> response = restTemplate.exchange(
                this.URL + "/docteurs/" + chatgroupe.getPlgcSFKDocteur(),
                HttpMethod.GET,
                entity,
                Docteur.class
        );
        Docteur docteur = response.getBody();
        return SadmChatGroupeResponseOM.builder()
                .plgcSpkchatGroupe(chatgroupe.getPlgcSpkchatGroupe())
                .plgcCtitre(chatgroupe.getPlgcCtitre())
                .docteur(docteur)
                .plgcXdateCreation(chatgroupe.getPlgcXdateCreation())
                .plgcBarchive(chatgroupe.getPlgcBarchive())
                .comCwhoCreate(chatgroupe.getComCwhoCreate())
                .comXwhenCreate(chatgroupe.getComXwhenCreate())
                .comCwhoUpdate(chatgroupe.getComCwhoUpdate())
                .comXwhenUpdate(chatgroupe.getComXwhenUpdate())
                .comSdesactive(chatgroupe.getComSdesactive())
                . build();

    }

    @Override
    public void deleteChatGroupe(Long id) {
        // Suppression d'un groupe de chat par son identifiant
        SadmChatGroupeOM chatGroupe = chatGroupeRepository.findById(id).orElse(null);
        if (chatGroupe != null) {
            chatGroupeRepository.delete(chatGroupe);
        }
    }

    @Override
    public List<SadmChatGroupeResponseOM> getAllChatbyDocteurId(Long id,String jwtToken) {
        // Récupération de tous les groupes de chat pour un docteur donné par son identifiant
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        List<SadmChatGroupeOM> chatgroupe = chatGroupeRepository.findByDocteurId(id);
        ResponseEntity<Docteur[]> response = restTemplate.exchange(
                this.URL + "/docteurs/all",
                HttpMethod.GET,
                entity,
                Docteur[].class
        );
        Docteur[] docteur = response.getBody();
        return chatgroupe.stream().map((SadmChatGroupeOM chatgroup) ->
                mapToCarResponse(chatgroup, docteur)).toList();
    }

    @Override
    public String Archiver(Long id) {
        // Archivage ou désarchivage d'un groupe de chat par son identifiant
        SadmChatGroupeOM chatGroupe = chatGroupeRepository.findById(id).orElse(null);
        if (chatGroupe != null) {
            boolean newFavoriteValue = !chatGroupe.getPlgcBarchive();
            chatGroupe.setPlgcBarchive(newFavoriteValue);
            chatGroupeRepository.save(chatGroupe);
            return newFavoriteValue ? "Cette réponse a été enregistrée dans tes favoris."
                    : "Cette réponse a été retirée de tes favoris.";
        }
        return "Chathistorique not found with id: " + id;

    }

    @Override
    public List<SadmChatGroupeResponseOM> getAllChatbyDocteurIdArchiver(Long id,String jwtToken) {
        // Récupération de tous les groupes de chat archivés pour un docteur donné par son identifiant
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        List<SadmChatGroupeOM> chatgroupe = chatGroupeRepository.findByPlgcBarchive(id);
        ResponseEntity<Docteur[]> response = restTemplate.exchange(
                this.URL + "/docteurs/all",
                HttpMethod.GET,
                entity,
                Docteur[].class
        );
        Docteur[] docteur = response.getBody();
        return chatgroupe.stream().map((SadmChatGroupeOM chatgroup) ->
                mapToCarResponse(chatgroup, docteur)).toList();
    }


    private SadmChatGroupeResponseOM mapToCarResponse(SadmChatGroupeOM chatgroup, Docteur[] docteurs) {
        Docteur foundDocteur = Arrays.stream(docteurs)
                .filter(docteur -> docteur.getPldtSpkdocteur().equals(chatgroup.getPlgcSFKDocteur()))
                .findFirst ()
                .orElse(null);

        return SadmChatGroupeResponseOM. builder ()
                .plgcSpkchatGroupe(chatgroup.getPlgcSpkchatGroupe())
                .plgcCtitre(chatgroup.getPlgcCtitre())
                .docteur(foundDocteur)
                .plgcXdateCreation(chatgroup.getPlgcXdateCreation())
                .plgcBarchive(chatgroup.getPlgcBarchive())
                .comCwhoCreate(chatgroup.getComCwhoCreate())
                .comXwhenCreate(chatgroup.getComXwhenCreate())
                .comCwhoUpdate(chatgroup.getComCwhoUpdate())
                .comXwhenUpdate(chatgroup.getComXwhenUpdate())
                .comSdesactive(chatgroup.getComSdesactive())
                . build();
    }



}