package com.dm.dossier_medical.controller;

import com.dm.dossier_medical.jpa.SadmNoteCliniqueDTO;
import com.dm.dossier_medical.jpa.SadmNoteCliniqueOM;
import com.dm.dossier_medical.models.SadmNoteCliniqueOMResponse;
import com.dm.dossier_medical.service.SadmNoteCliniqueOMService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Note_Clinique")
public class SadmNoteCliniqueOMController {

    @Autowired
    private SadmNoteCliniqueOMService sadmNoteCliniqueOMService;

    @PostMapping("/add")
    public ResponseEntity<SadmNoteCliniqueOM> saveDossier(@RequestBody SadmNoteCliniqueDTO nc) {
        SadmNoteCliniqueOM savenoteclinique = sadmNoteCliniqueOMService.savenoteclinique(nc);
        return new ResponseEntity<>(savenoteclinique, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SadmNoteCliniqueOMResponse>> getAllDossiers(HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmNoteCliniqueOMResponse> allNoteCliniques = sadmNoteCliniqueOMService.getallSadmNoteClinique(jwtToken);
        return ResponseEntity.ok(allNoteCliniques);
    }

    @GetMapping("{id}")
    public ResponseEntity<SadmNoteCliniqueOMResponse> findById(@PathVariable Long id , HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        SadmNoteCliniqueOMResponse Dossier = sadmNoteCliniqueOMService.getSadmNoteClinique(id,jwtToken);
        if (Dossier != null) {
            return new ResponseEntity<>(Dossier, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDossier(@PathVariable Long id) {
        sadmNoteCliniqueOMService.deleteSadmNoteClinique(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/Dossier_Medical/{id}")
    public ResponseEntity<List<SadmNoteCliniqueOMResponse>> findByDossierid(@PathVariable Long id, HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmNoteCliniqueOMResponse> Dossier = sadmNoteCliniqueOMService.getNotecliniquebyDossierId(id,jwtToken);
        if (Dossier != null) {
            return new ResponseEntity<>(Dossier, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SadmNoteCliniqueOM> updateNoteClinique(@PathVariable Long id, @RequestBody SadmNoteCliniqueOM sadmNoteClinique) {
        SadmNoteCliniqueOM updatedNoteClinique = sadmNoteCliniqueOMService.UpdateNoteclinique(id, sadmNoteClinique);
        return ResponseEntity.ok(updatedNoteClinique);
    }

    private String extractJwtTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Extract token from "Bearer "
        }
        return null;
    }

}
