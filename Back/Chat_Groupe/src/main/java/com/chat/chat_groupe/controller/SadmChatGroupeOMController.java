package com.chat.chat_groupe.controller;

import com.chat.chat_groupe.jpa.SadmChatGroupeOM;
import com.chat.chat_groupe.models.SadmChatGroupeResponseOM;
import com.chat.chat_groupe.service.SadmChatGroupeOMService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatgroupes")
public class SadmChatGroupeOMController {

    @Autowired
    private SadmChatGroupeOMService chatGroupeService;

    // Ajouter un nouveau groupe de chat
    @PostMapping("/add")
    public ResponseEntity<SadmChatGroupeOM> addChatGroupe(@RequestBody SadmChatGroupeOM chatGroupe) {
        SadmChatGroupeOM savedChatGroupe = chatGroupeService.saveChatGroupe(chatGroupe);
        return new ResponseEntity<>(savedChatGroupe, HttpStatus.CREATED);
    }

    // Obtenir un groupe de chat par son ID
    @GetMapping("/{id}")
    public ResponseEntity<SadmChatGroupeResponseOM> getChatGroupeById(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        String jwtToken = extractJwtTokenFromHeader(request);
        SadmChatGroupeResponseOM chatGroupe = chatGroupeService.getChatGroupeById(id, jwtToken);
        if (chatGroupe != null) {
            return new ResponseEntity<>(chatGroupe, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    // Obtenir tous les groupes de chat
    @GetMapping("/all")
    public ResponseEntity<List<SadmChatGroupeResponseOM>> getAllDocteurs(HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmChatGroupeResponseOM> allChat = chatGroupeService.getAllChat(jwtToken);
        return ResponseEntity.ok(allChat);
    }

    // Trouver un groupe de chat par son titre
    @GetMapping("/chat-groupe")
    public ResponseEntity<SadmChatGroupeResponseOM> findByTitre(@RequestParam String titre,HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        SadmChatGroupeResponseOM chatGroupe = chatGroupeService.findByTitre(titre,jwtToken);
        if (chatGroupe != null) {
            return new ResponseEntity<>(chatGroupe, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Supprimer un groupe de chat par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChatGroupe(@PathVariable Long id) {
        chatGroupeService.deleteChatGroupe(id);
        return ResponseEntity.noContent().build();
    }

    // Obtenir tous les groupes de chat d'un docteur par l'ID du docteur
    @GetMapping("/Docteur/{id}")
    public ResponseEntity<List<SadmChatGroupeResponseOM>> getChatGroupeByDocteurId(@PathVariable Long id,HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmChatGroupeResponseOM> chatGroupe = chatGroupeService.getAllChatbyDocteurId(id,jwtToken);
        if (chatGroupe != null) {
            return new ResponseEntity<>(chatGroupe, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Archiver ou désarchiver un groupe de chat par son ID
    @PutMapping("/{id}/archive")
    public ResponseEntity<String> archiverChatGroupe(@PathVariable Long id) {
        String message = chatGroupeService.Archiver(id);
        JSONObject jObjResponse = new JSONObject();
        jObjResponse.put("code", 200);
        jObjResponse.put("message", message);

        return ResponseEntity.ok(jObjResponse.toString());
    }

    // Obtenir tous les groupes de chat archivés d'un docteur par l'ID du docteur
    @GetMapping("/Docteur/{id}/Archiver")
    public ResponseEntity<List<SadmChatGroupeResponseOM>> getAllChatbyDocteurIdArchiver(@PathVariable Long id,HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmChatGroupeResponseOM> chatGroupe = chatGroupeService.getAllChatbyDocteurIdArchiver(id,jwtToken);
        if (chatGroupe != null) {
            return new ResponseEntity<>(chatGroupe, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@GetMapping
	public String hello()
	{
		return "djsfnjdfujudsjudfgdfdgfg";
	}

    private String extractJwtTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Extract token from "Bearer "
        }
        return null;
    }

}
