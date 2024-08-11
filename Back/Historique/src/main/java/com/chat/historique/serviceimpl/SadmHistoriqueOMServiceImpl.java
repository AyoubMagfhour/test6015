package com.chat.historique.serviceimpl;

import com.chat.historique.jpa.Chat_groupe;
import com.chat.historique.jpa.Docteur;
import com.chat.historique.jpa.SadmHistoriqueOM;
import com.chat.historique.jpa.System;
import com.chat.historique.models.SadmHistoriqueResponseOM;
import com.chat.historique.repository.SadmHistoriqueOMRepository;
import com.chat.historique.service.SadmHistoriqueOMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SadmHistoriqueOMServiceImpl implements SadmHistoriqueOMService {

    @Autowired
    private SadmHistoriqueOMRepository historiqueRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String CHAT_URL = "http://localhost:8888/SERVICE-CHAT-GROUPE/chatgroupes/";
    private final String DOCTEUR_URL = "http://localhost:8888/SERVICE-USER/docteurs/";
    private final String SYSTEM_URL = "http://localhost:8888/SERVICE-SYSTEM/systems/";

    @Override
    public SadmHistoriqueOM saveHistorique(SadmHistoriqueOM historique) {
        // Sauvegarde d'un historique
        return historiqueRepository.save(historique);
    }

    @Override
    public SadmHistoriqueResponseOM getHistoriqueById(Long id,String jwtToken) {
        // Récupération d'un historique par son identifiant
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        SadmHistoriqueOM historique =historiqueRepository.findById(id).orElse(null);
        ResponseEntity<Docteur> response = restTemplate.exchange(
                this.DOCTEUR_URL  + historique.getPlhqSfkDocteur(),
                HttpMethod.GET,
                entity,
                Docteur.class
        );
        Docteur docteur = response.getBody();
        System system =
                restTemplate.getForObject(this.SYSTEM_URL + historique.getPlhqSfkSystem(), System.class);
        ResponseEntity<Chat_groupe> chat = restTemplate.exchange(
                this.CHAT_URL  + historique.getPlhqSfkChatGroupe(),
                HttpMethod.GET,
                entity,
                Chat_groupe.class
        );
        Chat_groupe chats = chat.getBody();
        return SadmHistoriqueResponseOM.builder()
                .plhqSpkhistorique(historique.getPlhqSpkhistorique())
                .plhqSfkinstance(historique.getPlhqSfkinstance())
                .plhqBlimage(historique.getPlhqBlimage())
                .plhqCfavorite(historique.getPlhqCfavorite())
                .plhqCquestion(historique.getPlhqCquestion())
                .plhqCreponse(historique.getPlhqCreponse())
                .plhqCtypePhoto(historique.getPlhqCtypePhoto())
                .plhqXdateRequest(historique.getPlhqXdateRequest())
                .plhqXdateEnregistrementFavorite(historique.getPlhqXdateEnregistrementFavorite())
                .comCwhoCreate(historique.getComCwhoCreate())
                .comXwhenCreate(historique.getComXwhenCreate())
                .comCwhoUpdate(historique.getComCwhoUpdate())
                .comXwhenUpdate(historique.getComXwhenUpdate())
                .comSdesactive(historique.getComSdesactive())
                .plhqSfkChatGroupe(chats)
                .plhqSfkDocteur(docteur)
                .plhqSfkSystem(system)
                . build();
    }

    @Override
    public List<SadmHistoriqueResponseOM> getAllHistorique(String jwtToken) {
        // Récupération de tous les historiques
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<SadmHistoriqueOM> historiques = historiqueRepository.findAll();
        ResponseEntity<Docteur[]> response = restTemplate.exchange(
                this.DOCTEUR_URL + "all",
                HttpMethod.GET,
                entity,
                Docteur[].class
        );

        ResponseEntity<System[]> response_system =
                restTemplate.getForEntity(SYSTEM_URL + "all", System[].class);
        ResponseEntity<Chat_groupe[]> response_chat =
                restTemplate.exchange(CHAT_URL + "all",HttpMethod.GET,
                        entity, Chat_groupe[].class);
        Docteur[] docteur = response.getBody();
        System[] system = response_system.getBody();
        Chat_groupe[] chat = response_chat.getBody();
        return historiques.stream().map((SadmHistoriqueOM historique) ->
                mapToCarResponse(historique, docteur,system,chat)).toList();

    }

    @Override
    public void deleteHistorique(Long id) {
        // Suppression d'un historique par son identifiant
        SadmHistoriqueOM historique = historiqueRepository.findById(id).orElse(null);
        if (historique != null) {
            historiqueRepository.delete(historique);
        }
    }


    @Override
    public String toggleFavorite(Long id, Date Enregistrement) {
        // Bascule l'état "favorite" d'un historique
        SadmHistoriqueOM chathistorique = historiqueRepository.findById(id).orElse(null);
        if (chathistorique != null) {
            boolean newFavoriteValue = !chathistorique.getPlhqCfavorite();
            chathistorique.setPlhqCfavorite(newFavoriteValue);
            chathistorique.setPlhqXdateEnregistrementFavorite(Enregistrement);
            historiqueRepository.save(chathistorique);
            return newFavoriteValue ? "Cette réponse a été enregistrée dans tes favoris."
                    : "Cette réponse a été retirée de tes favoris.";
        }
        return "Chathistorique not found with id: " + id;
    }

    @Override
    public List<SadmHistoriqueResponseOM> searchByKeywordInResponse(String keyword, Long docteurID , String jwtToken) {
        // Recherche des historiques par mot-clé dans la réponse et par email du docteur
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<SadmHistoriqueOM> historiques =  historiqueRepository.findByKeywordAndDocteuremail(keyword,docteurID);
        ResponseEntity<Docteur[]> response = restTemplate.exchange(
                this.DOCTEUR_URL + "all",
                HttpMethod.GET,
                entity,
                Docteur[].class
        );
        ResponseEntity<System[]> response_system =
                restTemplate.getForEntity(SYSTEM_URL + "all", System[].class);
        ResponseEntity<Chat_groupe[]> response_chat =
                restTemplate.exchange(CHAT_URL + "all",HttpMethod.GET,
                        entity, Chat_groupe[].class);
        Docteur[] docteur = response.getBody();
        System[] system = response_system.getBody();
        Chat_groupe[] chat = response_chat.getBody();
        return historiques.stream().map((SadmHistoriqueOM historique) ->
                mapToCarResponse(historique, docteur,system,chat)).toList();
    }

    @Override
    public List<SadmHistoriqueResponseOM> getHistoriqueByDoctorEmail(Long docteurID, String jwtToken) {
        // Récupération des historiques par email du docteur
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<SadmHistoriqueOM> historiques = historiqueRepository.findByDocteuremail(docteurID);
        ResponseEntity<Docteur[]> response = restTemplate.exchange(
                this.DOCTEUR_URL + "all",
                HttpMethod.GET,
                entity,
                Docteur[].class
        );
        ResponseEntity<System[]> response_system =
                restTemplate.getForEntity(SYSTEM_URL + "all", System[].class);
        ResponseEntity<Chat_groupe[]> response_chat =
                restTemplate.exchange(CHAT_URL + "all",HttpMethod.GET,
                        entity, Chat_groupe[].class);
        Docteur[] docteur = response.getBody();
        System[] system = response_system.getBody();
        Chat_groupe[] chat = response_chat.getBody();
        return historiques.stream().map((SadmHistoriqueOM historique) ->
                mapToCarResponse(historique, docteur,system,chat)).toList();
    }

    @Override
    public List<SadmHistoriqueResponseOM> searchByReponse(String plhqCReponse , String jwtToken) {
        // Recherche des historiques par contenu de la réponse
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<SadmHistoriqueOM> historiques = historiqueRepository.findByPlhqCreponseContaining(plhqCReponse);
        ResponseEntity<Docteur[]> response = restTemplate.exchange(
                this.DOCTEUR_URL + "all",
                HttpMethod.GET,
                entity,
                Docteur[].class
        );
        ResponseEntity<System[]> response_system =
                restTemplate.getForEntity(SYSTEM_URL + "all", System[].class);
        ResponseEntity<Chat_groupe[]> response_chat =
                restTemplate.exchange(CHAT_URL + "all",HttpMethod.GET,
                        entity, Chat_groupe[].class);
        Docteur[] docteur = response.getBody();
        System[] system = response_system.getBody();
        Chat_groupe[] chat = response_chat.getBody();
        return historiques.stream().map((SadmHistoriqueOM historique) ->
                mapToCarResponse(historique, docteur,system,chat)).toList();
    }

//    @Override
//    public List<SadmHistoriqueResponseOM> findAllChatbyTitre(String titre) {
//        // Récupération des historiques par titre du groupe de chat
//        List<SadmHistoriqueOM> historiques = historiqueRepository.findByChatgroupeTitre(titre);
//        ResponseEntity<Docteur[]> response =
//                restTemplate.getForEntity(DOCTEUR_URL + "all", Docteur[].class);
//        ResponseEntity<System[]> response_system =
//                restTemplate.getForEntity(SYSTEM_URL + "all", System[].class);
//        ResponseEntity<Chat_groupe[]> response_chat =
//                restTemplate.getForEntity(CHAT_URL + "all", Chat_groupe[].class);
//        Docteur[] docteur = response.getBody();
//        System[] system = response_system.getBody();
//        Chat_groupe[] chat = response_chat.getBody();
//        return historiques.stream().map((SadmHistoriqueOM historique) ->
//                mapToCarResponse(historique, docteur,system,chat)).toList();
//    }

    @Override
    public List<SadmHistoriqueResponseOM> findFavoriteHistoriques(String jwtToken) {
        // Récupération de tous les historiques marqués comme favoris
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<SadmHistoriqueOM> historiques = historiqueRepository.findByPlhqCfavoriteTrue();
        ResponseEntity<Docteur[]> response = restTemplate.exchange(
                this.DOCTEUR_URL + "all",
                HttpMethod.GET,
                entity,
                Docteur[].class
        );
        ResponseEntity<System[]> response_system =
                restTemplate.getForEntity(SYSTEM_URL + "all", System[].class);
        ResponseEntity<Chat_groupe[]> response_chat =
                restTemplate.exchange(CHAT_URL + "all",HttpMethod.GET,
                        entity, Chat_groupe[].class);
        Docteur[] docteur = response.getBody();
        System[] system = response_system.getBody();
        Chat_groupe[] chat = response_chat.getBody();
        return historiques.stream().map((SadmHistoriqueOM historique) ->
                mapToCarResponse(historique, docteur,system,chat)).toList();
    }

    @Override
    public List<SadmHistoriqueResponseOM> findFavoriteHistoriquesbyDocteurEmail(Long docteurID,String jwtToken) {
        // Récupération des historiques marqués comme favoris par email du docteur
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<SadmHistoriqueOM> historiques = historiqueRepository.findByDocteurfavorite(docteurID);
        ResponseEntity<Docteur[]> response = restTemplate.exchange(
                this.DOCTEUR_URL + "all",
                HttpMethod.GET,
                entity,
                Docteur[].class
        );
        ResponseEntity<System[]> response_system =
                restTemplate.getForEntity(SYSTEM_URL + "all", System[].class);
        ResponseEntity<Chat_groupe[]> response_chat =
                restTemplate.exchange(CHAT_URL + "all",HttpMethod.GET,
                        entity, Chat_groupe[].class);
        Docteur[] docteur = response.getBody();
        System[] system = response_system.getBody();
        Chat_groupe[] chat = response_chat.getBody();
        return historiques.stream().map((SadmHistoriqueOM historique) ->
                mapToCarResponse(historique, docteur,system,chat)).toList();
    }

    @Override
    public List<SadmHistoriqueResponseOM> findAllChatbyId(Long id,String jwtToken) {
        // Récupération des historiques par identifiant du groupe de chat
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<SadmHistoriqueOM> historiques = historiqueRepository.findByplhqSfkChatGroupe(id);
        ResponseEntity<Docteur[]> response = restTemplate.exchange(
                this.DOCTEUR_URL + "all",
                HttpMethod.GET,
                entity,
                Docteur[].class
        );
        ResponseEntity<System[]> response_system =
                restTemplate.getForEntity(SYSTEM_URL + "all", System[].class);
        ResponseEntity<Chat_groupe[]> response_chat =
                restTemplate.exchange(CHAT_URL + "all",HttpMethod.GET,
                        entity, Chat_groupe[].class);
        Docteur[] docteur = response.getBody();
        System[] system = response_system.getBody();
        Chat_groupe[] chat = response_chat.getBody();
        return historiques.stream().map((SadmHistoriqueOM historique) ->
                mapToCarResponse(historique, docteur,system,chat)).toList();
    }

    @Override
    public SadmHistoriqueResponseOM getLastEnregistrement(String jwtToken) {
        // Récupération du dernier enregistrement d'un historique
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        SadmHistoriqueOM historique = historiqueRepository.findLastEnregistrement();

        ResponseEntity<Docteur> response = restTemplate.exchange(
                this.DOCTEUR_URL + historique.getPlhqSfkDocteur(),
                HttpMethod.GET,
                entity,
                Docteur.class
        );
        Docteur docteur = response.getBody();

        System system =
                restTemplate.getForObject(this.SYSTEM_URL +  historique.getPlhqSfkSystem(), System.class);

        ResponseEntity<Chat_groupe> chat =
                restTemplate.exchange(this.CHAT_URL +  historique.getPlhqSfkChatGroupe(),HttpMethod.GET,
                        entity, Chat_groupe.class);

        Chat_groupe chats = chat.getBody();

        return SadmHistoriqueResponseOM. builder ()
                .plhqSpkhistorique(historique.getPlhqSpkhistorique())
                .plhqSfkinstance(historique.getPlhqSfkinstance())
                .plhqBlimage(historique.getPlhqBlimage())
                .plhqCfavorite(historique.getPlhqCfavorite())
                .plhqCquestion(historique.getPlhqCquestion())
                .plhqCreponse(historique.getPlhqCreponse())
                .plhqCtypePhoto(historique.getPlhqCtypePhoto())
                .plhqXdateRequest(historique.getPlhqXdateRequest())
                .plhqXdateEnregistrementFavorite(historique.getPlhqXdateEnregistrementFavorite())
                .comCwhoCreate(historique.getComCwhoCreate())
                .comXwhenCreate(historique.getComXwhenCreate())
                .comCwhoUpdate(historique.getComCwhoUpdate())
                .comXwhenUpdate(historique.getComXwhenUpdate())
                .comSdesactive(historique.getComSdesactive())
                .plhqSfkChatGroupe(chats)
                .plhqSfkDocteur(docteur)
                .plhqSfkSystem(system)
                . build();
    }

    @Override
    public void deleteAllHistoriquesByChatGroupe(Long chatGroupeId) {
        List<SadmHistoriqueOM> historiques = historiqueRepository.findByplhqSfkChatGroupe(chatGroupeId);
        if (!historiques.isEmpty()) {
            historiqueRepository.deleteAll(historiques);
        }
    }

//    @Override
//    public SadmHistoriqueOM updateHistoriqueById(Long historiqueId, SadmHistoriqueOM historique) {
//        // Mise à jour d'un historique par son identifiant
//        Optional<SadmHistoriqueOM> existingHistoriqueOptional = historiqueRepository.findById(historiqueId);
//        if (existingHistoriqueOptional.isPresent()) {
//            SadmHistoriqueOM existingHistorique = existingHistoriqueOptional.get();
//            existingHistorique.setPlhqCquestion(historique.getPlhqCquestion());
//            existingHistorique.setPlhqCreponse(historique.getPlhqCreponse());
//            existingHistorique.setPlhqXdateRequest(historique.getPlhqXdateRequest());
//            existingHistorique.setPlhqCfavorite(historique.getPlhqCfavorite());
//            existingHistorique.setPlhqCreponse(historique.getPlhqCreponse());
//            existingHistorique.setPlhqCtypePhoto(historique.getPlhqCtypePhoto());
//            existingHistorique.setDocteur(historique.getDocteur());
//            existingHistorique.setPlhqSfksystem(historique.getPlhqSfksystem());
//            existingHistorique.setChatGroupe(historique.getChatGroupe());
//            // Update other fields as needed
//
//            return historiqueRepository.save(existingHistorique);
//        } else {
//            return null;
//        }
//    }





    private SadmHistoriqueResponseOM mapToCarResponse(SadmHistoriqueOM historique, Docteur[] docteurs , System[] systems , Chat_groupe[] chats) {
        Docteur foundDocteur = Arrays.stream(docteurs)
                .filter(docteur -> docteur.getPldtSpkdocteur().equals(historique.getPlhqSfkDocteur()))
                .findFirst ()
                .orElse(null);

        System foundSystem = Arrays.stream(systems)
                .filter(systeme -> systeme.getPlstSpksystem().equals(historique.getPlhqSfkSystem()))
                .findFirst ()
                .orElse(null);

        Chat_groupe foundChat = Arrays.stream(chats)
                .filter(chat -> chat.getPlgcSpkchatGroupe().equals(historique.getPlhqSfkChatGroupe()))
                .findFirst ()
                .orElse(null);

        return SadmHistoriqueResponseOM. builder ()
                .plhqSpkhistorique(historique.getPlhqSpkhistorique())
                .plhqSfkinstance(historique.getPlhqSfkinstance())
                .plhqBlimage(historique.getPlhqBlimage())
                .plhqCfavorite(historique.getPlhqCfavorite())
                .plhqCquestion(historique.getPlhqCquestion())
                .plhqCreponse(historique.getPlhqCreponse())
                .plhqCtypePhoto(historique.getPlhqCtypePhoto())
                .plhqXdateRequest(historique.getPlhqXdateRequest())
                .plhqXdateEnregistrementFavorite(historique.getPlhqXdateEnregistrementFavorite())
                .comCwhoCreate(historique.getComCwhoCreate())
                .comXwhenCreate(historique.getComXwhenCreate())
                .comCwhoUpdate(historique.getComCwhoUpdate())
                .comXwhenUpdate(historique.getComXwhenUpdate())
                .comSdesactive(historique.getComSdesactive())
                .plhqSfkChatGroupe(foundChat)
                .plhqSfkDocteur(foundDocteur)
                .plhqSfkSystem(foundSystem)
                . build();
    }

}