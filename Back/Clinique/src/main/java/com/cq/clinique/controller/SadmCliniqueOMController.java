package com.cq.clinique.controller;

import com.cq.clinique.jpa.SadmCliniqueOM;
import com.cq.clinique.service.SadmCliniqueOMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinique")
public class SadmCliniqueOMController {

    @Autowired
    private SadmCliniqueOMService sadmCliniqueOMService;

    @PostMapping("/add")
    public ResponseEntity<SadmCliniqueOM> saveSystem(@RequestBody SadmCliniqueOM clinique) {
        SadmCliniqueOM saveClinique = sadmCliniqueOMService.saveclinique(clinique);
        return new ResponseEntity<>(saveClinique, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SadmCliniqueOM> getSystemById(@PathVariable Long id) {
        SadmCliniqueOM system = sadmCliniqueOMService.findbyId(id);
        return ResponseEntity.ok(system);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SadmCliniqueOM>> getSystem() {
        List<SadmCliniqueOM> allSystem = sadmCliniqueOMService.findAll();
        return ResponseEntity.ok(allSystem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSystem(@PathVariable Long id) {
        sadmCliniqueOMService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
