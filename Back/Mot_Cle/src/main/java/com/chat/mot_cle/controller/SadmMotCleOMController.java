package com.chat.mot_cle.controller;

import com.chat.mot_cle.SadmMotCleOMService.SadmMotCleOMService;
import com.chat.mot_cle.jpa.SadmMotCleOM;
import com.chat.mot_cle.models.SadmMotCleResponseOM;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motcles")
public class SadmMotCleOMController {

    @Autowired
    private SadmMotCleOMService service;

    @PostMapping
    public SadmMotCleOM save(@RequestBody SadmMotCleOM sadmMotCle) {
        return service.save(sadmMotCle);
    }

    @GetMapping
    public List<SadmMotCleResponseOM> findAll() {
        return service.findAll();
    }

    @GetMapping("/{keyword}")
    public List<SadmMotCleResponseOM> findByKeyword(@PathVariable String keyword) {
        return service.findByKeyword(keyword);
    }

    @PutMapping("/historique/{historiqueId}")
    public ResponseEntity<SadmMotCleOM> updateMotCleByHistoriqueId(@PathVariable Long historiqueId, @RequestBody String newMotCle) {
        SadmMotCleOM updatedMotCle = service.updateMotCleByHistoriqueId(historiqueId, newMotCle);
        if (updatedMotCle != null) {
            return ResponseEntity.ok(updatedMotCle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/historique/{historiqueId}")
    public ResponseEntity<SadmMotCleResponseOM> findByHistorique(@PathVariable Long historiqueId) {
        SadmMotCleResponseOM updatedMotCle = service.findByHistoriqueSPK(historiqueId);
        if (updatedMotCle != null) {
            return ResponseEntity.ok(updatedMotCle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/historique/keyword")
    public ResponseEntity<List<SadmMotCleResponseOM>> getHistoriqueBykeyWord(@RequestParam String word, HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmMotCleResponseOM> motcle = service.getHistoriquebykeyWord(word,jwtToken);
        if (motcle.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(motcle);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        service.deletemotclebyplmcSpkhistorique(id,jwtToken);
        return ResponseEntity.noContent().build();
    }

    private String extractJwtTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Extract token from "Bearer "
        }
        return null;
    }

}
