package com.dm.dossier_medical.serviceimpl;

import com.dm.dossier_medical.jpa.Docteur;
import com.dm.dossier_medical.jpa.SadmDossierMedicalOM;
import com.dm.dossier_medical.jpa.SadmNoteCliniqueDTO;
import com.dm.dossier_medical.jpa.SadmNoteCliniqueOM;
import com.dm.dossier_medical.models.SadmNoteCliniqueOMResponse;
import com.dm.dossier_medical.repository.SadmDossierMedicalOMRepository;
import com.dm.dossier_medical.repository.SadmNoteCliniqueOMRepository;
import com.dm.dossier_medical.service.SadmNoteCliniqueOMService;
import jakarta.persistence.EntityNotFoundException;
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
public class SadmNoteCliniqueOMSeriveImpl implements SadmNoteCliniqueOMService {

    @Autowired
    private SadmNoteCliniqueOMRepository _sadmNoteCliniqueOMRepository;

    @Autowired
    private SadmDossierMedicalOMRepository _sadmDossierMedicalOMRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String DOCTEUR_URL = "http://localhost:8888/SERVICE-USER/docteurs/";



    public SadmNoteCliniqueOM savenoteclinique(SadmNoteCliniqueDTO noteCliniqueDTO) {
        SadmDossierMedicalOM dossierMedical = _sadmDossierMedicalOMRepository.findById(noteCliniqueDTO.getPlncSFKDossier_Medical())
                .orElseThrow(() -> new EntityNotFoundException("DossierMedical not found"));

        SadmNoteCliniqueOM noteClinique = new SadmNoteCliniqueOM();
        noteClinique.setPlncCNote(noteCliniqueDTO.getPlncCNote());
        noteClinique.setPlncSFKDossier_Medical(dossierMedical);
        noteClinique.setPlncSFKDocteur(noteCliniqueDTO.getPlncSFKDocteur());
        noteClinique.setPlncBImage(noteCliniqueDTO.getPlncBImage());
        noteClinique.setComCwhoCreate(noteCliniqueDTO.getComCwhoCreate());
        noteClinique.setComXwhenCreate(noteCliniqueDTO.getComXwhenCreate());
        noteClinique.setComCwhoUpdate(noteCliniqueDTO.getComCwhoUpdate());
        noteClinique.setComXwhenUpdate(noteCliniqueDTO.getComXwhenUpdate());
        noteClinique.setComSdesactive(noteCliniqueDTO.getComSdesactive());

        return _sadmNoteCliniqueOMRepository.save(noteClinique);
    }

    @Override
    public List<SadmNoteCliniqueOMResponse> getallSadmNoteClinique(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        List<SadmNoteCliniqueOM> sadmNoteCliniques = _sadmNoteCliniqueOMRepository.findAll();
        ResponseEntity<Docteur[]> response = restTemplate.exchange(
                this.DOCTEUR_URL + "all",
                HttpMethod.GET,
                entity,
                Docteur[].class
        );
        Docteur[] docteur = response.getBody();
        return sadmNoteCliniques.stream().map((SadmNoteCliniqueOM DM) ->
                mapToCarResponse(DM, docteur)).toList();
    }

    @Override
    public SadmNoteCliniqueOMResponse getSadmNoteClinique(Long id,String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        SadmNoteCliniqueOM note = _sadmNoteCliniqueOMRepository.findById(id).orElse(null);
        ResponseEntity<Docteur> response = restTemplate.exchange(
                this.DOCTEUR_URL  + note.getPlncSFKDocteur(),
                HttpMethod.GET,
                entity,
                Docteur.class
        );
        Docteur docteur = response.getBody();

        return SadmNoteCliniqueOMResponse. builder ()
                .plncCNote(note.getPlncCNote())
                .plncSFKDossier_Medical(note.getPlncSFKDossier_Medical())
                .plncSFKDocteur(docteur)
                .plncBImage(note.getPlncBImage())
                .comCwhoCreate(note.getComCwhoCreate())
                .comCwhoUpdate(note.getComCwhoUpdate())
                .comXwhenUpdate(note.getComXwhenUpdate())
                .comXwhenCreate(note.getComXwhenCreate())
                .comSdesactive(note.getComSdesactive())
                . build();

    }

    @Override
    public void deleteSadmNoteClinique(Long id) {
        SadmNoteCliniqueOM sadmNoteClinique = _sadmNoteCliniqueOMRepository.findById(id).orElse(null);
        if (sadmNoteClinique != null) {
            _sadmNoteCliniqueOMRepository.delete(sadmNoteClinique);
        }
    }

    @Override
    public List<SadmNoteCliniqueOMResponse> getNotecliniquebyDossierId(Long id,String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        List<SadmNoteCliniqueOM> sadmNoteCliniques = _sadmNoteCliniqueOMRepository.findByDossierMedicalId(id);
        ResponseEntity<Docteur[]> response = restTemplate.exchange(
                this.DOCTEUR_URL + "all",
                HttpMethod.GET,
                entity,
                Docteur[].class
        );
        Docteur[] docteur = response.getBody();
        return sadmNoteCliniques.stream().map((SadmNoteCliniqueOM DM) ->
                mapToCarResponse(DM, docteur)).toList();
    }



    @Override
    public SadmNoteCliniqueOM UpdateNoteclinique(Long id, SadmNoteCliniqueOM sadmNoteClinique) {
        return _sadmNoteCliniqueOMRepository.findById(id)
                .map(existingNote -> {
                    existingNote.setPlncCNote(sadmNoteClinique.getPlncCNote());
                    existingNote.setPlncSFKDossier_Medical(sadmNoteClinique.getPlncSFKDossier_Medical());
                    existingNote.setPlncSFKDocteur(sadmNoteClinique.getPlncSFKDocteur());
                    existingNote.setPlncBImage(sadmNoteClinique.getPlncBImage());
                    existingNote.setComCwhoCreate(sadmNoteClinique.getComCwhoCreate());
                    existingNote.setComCwhoUpdate(sadmNoteClinique.getComCwhoUpdate());
                    existingNote.setComXwhenUpdate(sadmNoteClinique.getComXwhenUpdate());
                    existingNote.setComXwhenCreate(sadmNoteClinique.getComXwhenCreate());
                    existingNote.setComSdesactive(sadmNoteClinique.getComSdesactive());
                    return _sadmNoteCliniqueOMRepository.save(existingNote);
                })
                .orElse(null); // or throw an exception
    }


    private SadmNoteCliniqueOMResponse mapToCarResponse(SadmNoteCliniqueOM Dossier, Docteur[] docteur ) {
        Docteur foundDocteur = Arrays.stream(docteur)
                .filter(docteurs -> docteurs.getPldtSpkdocteur().equals(Dossier.getPlncSFKDocteur()))
                .findFirst ()
                .orElse(null);

        return SadmNoteCliniqueOMResponse. builder ()
                .plncSPKNoteClinique(Dossier.getPlncSPKNoteClinique())
                .plncCNote(Dossier.getPlncCNote())
                .plncSFKDossier_Medical(
                        Dossier.getPlncSFKDossier_Medical()
                )
                .plncSFKDocteur(foundDocteur)
                .plncBImage(Dossier.getPlncBImage())
                .comCwhoCreate(Dossier.getComCwhoCreate())
                .comCwhoUpdate(Dossier.getComCwhoUpdate())
                .comXwhenUpdate(Dossier.getComXwhenUpdate())
                .comXwhenCreate(Dossier.getComXwhenCreate())
                .comSdesactive(Dossier.getComSdesactive())
                . build();
    }
}
