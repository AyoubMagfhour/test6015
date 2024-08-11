package com.dm.dossier_medical.controller;

import com.dm.dossier_medical.jpa.SadmDossierMedicalOM;
import com.dm.dossier_medical.jpa.SadmNoteCliniqueOM;
import com.dm.dossier_medical.models.SadmDossierMedicalOMResponse;
import com.dm.dossier_medical.service.SadmDossierMedicalOMService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Dossier_Medical")
public class SadmDossierMedicalOMController {

    @Autowired
    private SadmDossierMedicalOMService service;

    @PostMapping("/add")
    public ResponseEntity<SadmDossierMedicalOM> saveDossier(@RequestBody SadmDossierMedicalOM dm) {
        SadmDossierMedicalOM saveClinique = service.AjouterDossier(dm);
        return new ResponseEntity<>(saveClinique, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SadmDossierMedicalOMResponse>> getAllDossiers(HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmDossierMedicalOMResponse> allHistoriques = service.getALLDossier(jwtToken);
        return ResponseEntity.ok(allHistoriques);
    }

    @GetMapping("{id}")
    public ResponseEntity<SadmDossierMedicalOMResponse> findById(@PathVariable Long id, HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        SadmDossierMedicalOMResponse Dossier = service.getDossierparID(id,jwtToken);
        if (Dossier != null) {
            return new ResponseEntity<>(Dossier, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<SadmDossierMedicalOMResponse> findByIdPatients(@PathVariable Long id, HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        SadmDossierMedicalOMResponse Dossier = service.getDossierparpatientID(id,jwtToken);
        if (Dossier != null) {
            return new ResponseEntity<>(Dossier, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDossier(@PathVariable Long id) {
        service.DeleteDossier(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SadmDossierMedicalOM> updateNoteClinique(@PathVariable Long id, @RequestBody SadmDossierMedicalOM sadmDossierMedicalOM) {
        SadmDossierMedicalOM updatedDossier = service.updateDossier(id, sadmDossierMedicalOM);
        return ResponseEntity.ok(updatedDossier);
    }

    @PutMapping("/{id}/whoupdate")
    public ResponseEntity<SadmDossierMedicalOM> updateNoteClinique2(@PathVariable Long id, @RequestBody SadmDossierMedicalOM sadmDossierMedicalOM) {
        SadmDossierMedicalOM updatedDossier = service.updateDossier2(id, sadmDossierMedicalOM);
        return ResponseEntity.ok(updatedDossier);
    }

    private String extractJwtTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Extract token from "Bearer "
        }
        return null;
    }
}
