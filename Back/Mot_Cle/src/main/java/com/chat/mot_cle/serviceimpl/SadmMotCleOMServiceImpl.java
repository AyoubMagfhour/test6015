package com.chat.mot_cle.serviceimpl;

import com.chat.mot_cle.SadmMotCleOMService.SadmMotCleOMService;
import com.chat.mot_cle.jpa.Historique;
import com.chat.mot_cle.jpa.SadmMotCleOM;
import com.chat.mot_cle.models.SadmMotCleResponseOM;
import com.chat.mot_cle.repository.SadmMotCleOMRepository;
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
public class SadmMotCleOMServiceImpl implements SadmMotCleOMService {

    @Autowired
    private SadmMotCleOMRepository motCleOMRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String HISTORIQUE_URL = "http://localhost:8888/SERVICE-HISTORIQUE/historiques/";


    @Override
    public SadmMotCleOM save(SadmMotCleOM sadmMotCle) {
        return motCleOMRepository.save(sadmMotCle);
    }

    @Override
    public List<SadmMotCleResponseOM> findAll() {

        List<SadmMotCleOM> mocles = motCleOMRepository.findAll();
        ResponseEntity<Historique[]> response =
                restTemplate.getForEntity(HISTORIQUE_URL + "all", Historique[].class);
        Historique[] historique = response.getBody();
        return mocles.stream().map((SadmMotCleOM motcle) ->
                mapToCarResponse(motcle, historique)).toList();
    }

    @Override
    public List<SadmMotCleResponseOM> findByKeyword(String keyword) {
        List<SadmMotCleOM> mocles =motCleOMRepository.findByPlmcCmotCle(keyword);
        ResponseEntity<Historique[]> response =
                restTemplate.getForEntity(HISTORIQUE_URL + "all", Historique[].class);
        Historique[] historique = response.getBody();
        return mocles.stream().map((SadmMotCleOM motcle) ->
                mapToCarResponse(motcle, historique)).toList();
    }

    @Override
    public SadmMotCleOM updateMotCleByHistoriqueId(Long historiqueId, String newMotCle) {
        SadmMotCleOM motCle = motCleOMRepository.findByPlmcSpkhistorique(historiqueId);
        if (motCle != null) {
            motCle.setPlmcCmotCle(motCle.getPlmcCmotCle() +','+newMotCle);
            motCleOMRepository.save(motCle);
        }
        return motCle;
    }

    @Override
    public SadmMotCleResponseOM findByHistoriqueSPK(Long historiqueId) {
        SadmMotCleOM mot = motCleOMRepository.findByPlmcSpkhistorique(historiqueId);
        Historique his =
                restTemplate.getForObject(this.HISTORIQUE_URL + mot.getPlmcSpkhistorique(), Historique.class);
        return SadmMotCleResponseOM. builder ()
                .plmcSpkmotCle(mot.getPlmcSpkmotCle())
                .plmcSfkinstance(mot.getPlmcSfkinstance())
                .plmcCmotCle(mot.getPlmcCmotCle())
                .comCwhoCreate(mot.getComCwhoCreate())
                .comXwhenCreate(mot.getComXwhenCreate())
                .comCwhoUpdate(mot.getComCwhoUpdate())
                .comXwhenUpdate(mot.getComXwhenUpdate())
                .comSdesactive(mot.getComSdesactive())
                .Historique(his)
                . build();
    }

    @Override
    public List<SadmMotCleResponseOM> getHistoriquebykeyWord(String keyword, String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<SadmMotCleOM> mocles = motCleOMRepository.findByPlmcCmotCleContaining(keyword);
        ResponseEntity<Historique[]> response =
                restTemplate.exchange(HISTORIQUE_URL + "all",HttpMethod.GET,
                        entity, Historique[].class);
        Historique[] historique = response.getBody();
        return mocles.stream().map((SadmMotCleOM motcle) ->
                mapToCarResponse(motcle, historique)).toList();
    }

    @Override
    public void deletemotclebyplmcSpkhistorique(Long historiqueid, String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Historique[]> response =
                restTemplate.exchange(HISTORIQUE_URL + "chat-groupe-id?id=" + historiqueid, HttpMethod.GET,
                        entity, Historique[].class);
        for (Historique historique : response.getBody()) {
            // Process each historique if needed
            System.out.println((historique == null) ? "null" : historique.toString());
            SadmMotCleOM mot = motCleOMRepository.findByPlmcSpkhistorique(historique.getPlhqSpkhistorique());
            if (mot != null) {
                motCleOMRepository.delete(mot);
            }
        }

    }

    private SadmMotCleResponseOM mapToCarResponse(SadmMotCleOM mot, Historique[] historiques) {
        Historique foundHistorique = Arrays.stream(historiques)
                .filter(historique -> historique.getPlhqSpkhistorique().equals(mot.getPlmcSpkhistorique()))
                .findFirst ()
                .orElse(null);

        return SadmMotCleResponseOM. builder ()
                .plmcSpkmotCle(mot.getPlmcSpkmotCle())
                .plmcSfkinstance(mot.getPlmcSfkinstance())
                .plmcCmotCle(mot.getPlmcCmotCle())
                .comCwhoCreate(mot.getComCwhoCreate())
                .comXwhenCreate(mot.getComXwhenCreate())
                .comCwhoUpdate(mot.getComCwhoUpdate())
                .comXwhenUpdate(mot.getComXwhenUpdate())
                .comSdesactive(mot.getComSdesactive())
                .Historique(foundHistorique)
                . build();
    }
}
