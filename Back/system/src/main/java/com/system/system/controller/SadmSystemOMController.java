package com.system.system.controller;

import com.system.system.jpa.SadmSystemOM;
import com.system.system.service.SadmSystemOMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systems")
public class SadmSystemOMController {

    @Autowired
    private SadmSystemOMService systemService;

    @PostMapping("/add")
    public ResponseEntity<SadmSystemOM> saveSystem(@RequestBody SadmSystemOM system) {
        SadmSystemOM savesystem = systemService.saveSystem(system);
        return new ResponseEntity<>(savesystem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SadmSystemOM> getSystemById(@PathVariable Long id) {
        SadmSystemOM system = systemService.getSystemById(id);
        return ResponseEntity.ok(system);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SadmSystemOM>> getSystem() {
        List<SadmSystemOM> allSystem = systemService.getAllSystem();
        return ResponseEntity.ok(allSystem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSystem(@PathVariable Long id) {
        systemService.deleteSystem(id);
        return ResponseEntity.noContent().build();
    }


}
