package com.chat.historique.controller;

import com.chat.historique.jpa.SadmHistoriqueOM;
import com.chat.historique.models.SadmHistoriqueResponseOM;
import com.chat.historique.service.SadmHistoriqueOMService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/historiques")
public class SadmHistoriqueOMController {

    @Autowired
    private SadmHistoriqueOMService historiqueService;

    @Autowired
    public SadmHistoriqueOMController(SadmHistoriqueOMService historiqueService) {
        this.historiqueService = historiqueService;
    }

    // Ajouter un nouvel historique
    @PostMapping("/add")
    public ResponseEntity<SadmHistoriqueOM> saveHistorique(@RequestBody SadmHistoriqueOM historique) {
        SadmHistoriqueOM savehistorique = historiqueService.saveHistorique(historique);
        return new ResponseEntity<>(savehistorique, HttpStatus.CREATED);
    }

    // Obtenir un historique par son ID
    @GetMapping("/{id}")
    public ResponseEntity<SadmHistoriqueResponseOM> getDocteurById(@PathVariable Long id,HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        SadmHistoriqueResponseOM historique = historiqueService.getHistoriqueById(id,jwtToken);
        return ResponseEntity.ok(historique);
    }

    // Obtenir tous les historiques
    @GetMapping("/all")
    public ResponseEntity<List<SadmHistoriqueResponseOM>> getAllDocteurs(HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmHistoriqueResponseOM> allHistoriques = historiqueService.getAllHistorique(jwtToken);
        return ResponseEntity.ok(allHistoriques);
    }

    // Supprimer un historique par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        historiqueService.deleteHistorique(id);
        return ResponseEntity.noContent().build();
    }

    // Basculer l'état favori d'un historique
    @PatchMapping("/{id}/toggle-favorite")
    public ResponseEntity<String> toggleFavorite(@PathVariable Long id, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date Enregistrement) {
        String message = historiqueService.toggleFavorite(id, Enregistrement);
        JSONObject jObjResponse = new JSONObject();
        jObjResponse.put("code", 200);
        jObjResponse.put("message", message);

        return ResponseEntity.ok(jObjResponse.toString());
    }

    // Rechercher des historiques par mot-clé dans la réponse
    @GetMapping("/search")
    public ResponseEntity<List<SadmHistoriqueResponseOM>> searchByKeywordInResponse(@RequestParam String keyword, @RequestParam Long docteurID,HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmHistoriqueResponseOM> searchResults = historiqueService.searchByKeywordInResponse(keyword, docteurID,jwtToken);
        return ResponseEntity.ok(searchResults);
    }

    // Obtenir les historiques par l'email du docteur
    @GetMapping("/doctor/{docteurID}")
    public ResponseEntity<List<SadmHistoriqueResponseOM>> getHistoriqueByDoctorEmail(@PathVariable Long docteurID,HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmHistoriqueResponseOM> historiqueList = historiqueService.getHistoriqueByDoctorEmail(docteurID,jwtToken);
        return ResponseEntity.ok().body(historiqueList);
    }

    // Rechercher des historiques par la réponse
    @GetMapping("/historique/search")
    public ResponseEntity<List<SadmHistoriqueResponseOM>> searchByReponse(@RequestParam String plhqCReponse,HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmHistoriqueResponseOM> historiques = historiqueService.searchByReponse(plhqCReponse,jwtToken);
        if (historiques.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(historiques);
        }
    }

    // Trouver des historiques par titre de chat-groupe
//    @GetMapping("/chat-groupe")
//    public ResponseEntity<List<SadmHistoriqueResponseOM>> findByTitre(@RequestParam String titre) {
//        List<SadmHistoriqueResponseOM> historiqueList = historiqueService.findAllChatbyTitre(titre);
//        if (historiqueList.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(historiqueList, HttpStatus.OK);
//        }
//    }

    // Trouver des historiques par ID de chat-groupe
    @GetMapping("/chat-groupe-id")
    public ResponseEntity<List<SadmHistoriqueResponseOM>> findById(@RequestParam Long id,HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmHistoriqueResponseOM> historiqueList = historiqueService.findAllChatbyId(id,jwtToken);
        if (historiqueList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(historiqueList, HttpStatus.OK);
        }
    }

    // Obtenir les historiques favoris
    @GetMapping("/favorite")
    public ResponseEntity<List<SadmHistoriqueResponseOM>> getFavoriteHistoriques(HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmHistoriqueResponseOM> favoriteHistoriques = historiqueService.findFavoriteHistoriques(jwtToken);
        if (favoriteHistoriques.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(favoriteHistoriques, HttpStatus.OK);
    }

    // Obtenir les historiques favoris par l'email du docteur
    @GetMapping("/favorite-by-docteur-email")
    public ResponseEntity<List<SadmHistoriqueResponseOM>> getFavoriteHistoriquesbuDocteurEmail(@RequestParam Long docteurID,HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmHistoriqueResponseOM> favoriteHistoriques = historiqueService.findFavoriteHistoriquesbyDocteurEmail(docteurID,jwtToken);
        if (favoriteHistoriques.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(favoriteHistoriques, HttpStatus.OK);
    }

    // Obtenir le dernier enregistrement
    @GetMapping("/last")
    public ResponseEntity<SadmHistoriqueResponseOM> getLastEnregistrement(HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        SadmHistoriqueResponseOM lastEnregistrement = historiqueService.getLastEnregistrement(jwtToken);
        if (lastEnregistrement != null) {
            return ResponseEntity.ok(lastEnregistrement);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Mettre à jour un historique par son ID
//    @PutMapping("/update/{historiqueId}")
//    public ResponseEntity<SadmHistoriqueOM> updateHistorique(@PathVariable Long historiqueId, @RequestBody SadmHistoriqueOM historique) {
//        SadmHistoriqueOM updatedHistorique = historiqueService.updateHistoriqueById(historiqueId, historique);
//        if (updatedHistorique != null) {
//            return new ResponseEntity<>(updatedHistorique, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("/chatgroupes/{chatGroupeId}/historiques")
    public ResponseEntity<String> deleteAllHistoriquesByChatGroupe(@PathVariable Long chatGroupeId) {
        historiqueService.deleteAllHistoriquesByChatGroupe(chatGroupeId);
        return ResponseEntity.noContent().build();    }



    private String extractJwtTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Extract token from "Bearer "
        }
        return null;
    }
}