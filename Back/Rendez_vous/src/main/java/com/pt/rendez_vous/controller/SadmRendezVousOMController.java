package com.pt.rendez_vous.controller;

import com.pt.rendez_vous.jpa.SadmRendezVousOM;
import com.pt.rendez_vous.models.SadmRendezVousOMResponse;
import com.pt.rendez_vous.service.SadmRendezVousOMService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/render_vous")
public class SadmRendezVousOMController {

    @Autowired
    private SadmRendezVousOMService sadmRendezVousOMService;


    @PostMapping("/add")
    public ResponseEntity<SadmRendezVousOM> saveSystem(@RequestBody SadmRendezVousOM rendezVousOM) {
        SadmRendezVousOM saveClinique = sadmRendezVousOMService.prendRendezVous(rendezVousOM);
        return new ResponseEntity<>(saveClinique, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SadmRendezVousOMResponse>> getAllDocteurs(HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmRendezVousOMResponse> allHistoriques = sadmRendezVousOMService.getALLSadmRendezVous(jwtToken);
        return ResponseEntity.ok(allHistoriques);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<SadmRendezVousOMResponse>> getAllbycliniqueid(@PathVariable Long id, HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmRendezVousOMResponse> allHistoriques = sadmRendezVousOMService.getAllbycliniqueid(id,jwtToken);
        return ResponseEntity.ok(allHistoriques);
    }

    @GetMapping("{id}")
    public ResponseEntity<SadmRendezVousOMResponse> findById(@PathVariable Long id, HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        SadmRendezVousOMResponse historiqueList = sadmRendezVousOMService.getSadmRendezVous(id,jwtToken);
        if (historiqueList != null) {
            return new ResponseEntity<>(historiqueList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleterendervous(@PathVariable Long id) {
        sadmRendezVousOMService.DeleteSadmRendezVous(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/patient/{id}")
    public ResponseEntity<Void> deleterendervousbypatientid(@PathVariable Long id) {
        sadmRendezVousOMService.DeleteSadmRendezVousbyPatientid(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check-overlap")
    public ResponseEntity<List<SadmRendezVousOM>> checkOverlap(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate) {

        List<SadmRendezVousOM> overlappingAppointments = sadmRendezVousOMService.findOverlappingAppointments(startDate, endDate);
        return ResponseEntity.ok(overlappingAppointments);
    }

    @GetMapping("/future/{idclinique}")
    public ResponseEntity<List<SadmRendezVousOMResponse>> getFutureAppointments(@PathVariable Long idclinique,HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromHeader(request);
        List<SadmRendezVousOMResponse> futureAppointments = sadmRendezVousOMService.getNextAppointment(idclinique,jwtToken);
        return ResponseEntity.ok(futureAppointments);
    }

    @GetMapping("/clinic/{idclinique}/countToday")
    public ResponseEntity<Long> countAppointmentsForCurrentDay(@PathVariable Long idclinique) {
        long count = sadmRendezVousOMService.countAppointmentsForCurrentDay(idclinique);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/clinic/{idclinique}/countTodayEncours")
    public ResponseEntity<Long> countAppointmentsForCurrentDayencours(@PathVariable Long idclinique) {
        long count = sadmRendezVousOMService.countAppointmentsForCurrentDayencours(idclinique);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/clinic/{idclinique}/countTodayend")
    public ResponseEntity<Long> countAppointmentsForCurrentDayend(@PathVariable Long idclinique) {
        long count = sadmRendezVousOMService.countAppointmentsForCurrentDayend(idclinique);
        return ResponseEntity.ok(count);
    }

    private String extractJwtTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Extract token from "Bearer "
        }
        return null;
    }

}
