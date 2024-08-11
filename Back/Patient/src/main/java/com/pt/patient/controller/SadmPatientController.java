package com.pt.patient.controller;

import com.pt.patient.jpa.SadmPatientOM;
import com.pt.patient.models.SadmPatientOMResponse;
import com.pt.patient.service.SadmPatientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patients")
public class SadmPatientController {

    @Autowired
    private SadmPatientService service;

    @PostMapping
    public ResponseEntity<SadmPatientOM> createPatient(@RequestBody SadmPatientOM patient) {
        return ResponseEntity.ok(service.savePatient(patient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SadmPatientOM> updatePatient(@PathVariable Long id, @RequestBody SadmPatientOM patient) {
        patient.setPlptSFKPatient(id);
        return ResponseEntity.ok(service.updatePatient(patient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id, HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        service.deletePatient(id,jwtToken);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SadmPatientOMResponse> getPatientById(@PathVariable Long id, HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        SadmPatientOMResponse pt = service.getPatientById(id,jwtToken);
        if (pt != null) {
            return new ResponseEntity<>(pt, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<SadmPatientOMResponse>> getAllPatients(HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmPatientOMResponse> pt = service.getAllPatients(jwtToken);
        return ResponseEntity.ok(pt);
    }

    @PostMapping("/visage")
    public ResponseEntity<SadmPatientOMResponse> loginByFace(@RequestBody float[] faceDescriptor,HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        SadmPatientOMResponse utilisateur = service.findpatientbyface(faceDescriptor , jwtToken);
        return utilisateur != null ? ResponseEntity.ok(utilisateur) : ResponseEntity.status(401).build();
    }


    private String extractJwtTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Extract token from "Bearer "
        }
        return null;
    }
}
