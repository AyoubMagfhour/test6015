package com.dm.dossier_medical.serviceimpl;

import com.dm.dossier_medical.jpa.Patients;
import com.dm.dossier_medical.jpa.SadmDossierMedicalOM;
import com.dm.dossier_medical.models.SadmDossierMedicalOMResponse;
import com.dm.dossier_medical.repository.SadmDossierMedicalOMRepository;
import com.dm.dossier_medical.service.SadmDossierMedicalOMService;
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
public class SadmDossierMedicalOMServiceImpl implements SadmDossierMedicalOMService {

    @Autowired
    private SadmDossierMedicalOMRepository dossierMedicalOMRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String PATIENT_URL = "http://localhost:8888/SERVICE-PATIENT/api/patients";

    @Override
    public SadmDossierMedicalOM AjouterDossier(SadmDossierMedicalOM rv) {
        return dossierMedicalOMRepository.save(rv);
    }

    @Override
    public List<SadmDossierMedicalOMResponse> getALLDossier(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<SadmDossierMedicalOM> dm = dossierMedicalOMRepository.findAll();

        ResponseEntity<Patients[]> patient = restTemplate.exchange(
                this.PATIENT_URL,
                HttpMethod.GET,
                entity,
                Patients[].class
        );
        Patients[] patients = patient.getBody();


        return dm.stream().map((SadmDossierMedicalOM rv) ->
                mapToCarResponse(rv, patients)).toList();
    }

    @Override
    public SadmDossierMedicalOMResponse getDossierparID(Long id, String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        SadmDossierMedicalOM re = dossierMedicalOMRepository.findById(id).orElse(null);

        ResponseEntity<Patients> response = restTemplate.exchange(
                this.PATIENT_URL + "/"  + re.getPldmSFKPatients(),
                HttpMethod.GET,
                entity,
                Patients.class
        );
        Patients patient = response.getBody();

        return  SadmDossierMedicalOMResponse.builder()
                .pldmSFKDossierMedical(re.getPldmSFKDossierMedical())
                .pldmCAllergies(re.getPldmCAllergies())
                .pldmCAntecedentsMedicaux(re.getPldmCAntecedentsMedicaux())
                .pldmCMedicamentActuels(re.getPldmCMedicamentActuels())
                .pldmXDateCreation(re.getPldmXDateCreation())
                .comCwhoCreate(re.getComCwhoCreate())
                .comCwhoUpdate(re.getComCwhoUpdate())
                .comXwhenUpdate(re.getComXwhenUpdate())
                .comXwhenCreate(re.getComXwhenCreate())
                .comSdesactive(re.getComSdesactive())
                .pldmSFKPatients(patient)
                . build();
    }

    @Override
    public SadmDossierMedicalOMResponse getDossierparpatientID(Long id, String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        SadmDossierMedicalOM re = dossierMedicalOMRepository.findSadmDossierMedicalOMSByPldmSFKPatients(id);

        ResponseEntity<Patients> response = restTemplate.exchange(
                this.PATIENT_URL + "/"  + id,
                HttpMethod.GET,
                entity,
                Patients.class
        );
        Patients patient = response.getBody();

        return  SadmDossierMedicalOMResponse.builder()
                .pldmSFKDossierMedical(re.getPldmSFKDossierMedical())
                .pldmCAllergies(re.getPldmCAllergies())
                .pldmCAntecedentsMedicaux(re.getPldmCAntecedentsMedicaux())
                .pldmCMedicamentActuels(re.getPldmCMedicamentActuels())
                .pldmXDateCreation(re.getPldmXDateCreation())
                .comCwhoCreate(re.getComCwhoCreate())
                .comCwhoUpdate(re.getComCwhoUpdate())
                .comXwhenUpdate(re.getComXwhenUpdate())
                .comXwhenCreate(re.getComXwhenCreate())
                .comSdesactive(re.getComSdesactive())
                .pldmSFKPatients(patient)
                . build();
    }

    @Override
    public void DeleteDossier(Long id) {
        SadmDossierMedicalOM re = dossierMedicalOMRepository.findById(id).orElse(null);
        if(re != null) {
            dossierMedicalOMRepository.delete(re);
        }
    }

    @Override
    public SadmDossierMedicalOM updateDossier(Long id, SadmDossierMedicalOM rv) {
        return dossierMedicalOMRepository.findById(id)
                .map(existingNote -> {
                    existingNote.setPldmCAllergies(rv.getPldmCAllergies());
                    existingNote.setPldmCAntecedentsMedicaux(rv.getPldmCAntecedentsMedicaux());
                    existingNote.setPldmCMedicamentActuels(rv.getPldmCMedicamentActuels());
                    existingNote.setComCwhoUpdate(rv.getComCwhoUpdate());
                    existingNote.setComXwhenUpdate(rv.getComXwhenUpdate());
                    return dossierMedicalOMRepository.save(existingNote);
                })
                .orElse(null);
    }

    @Override
    public SadmDossierMedicalOM updateDossier2(Long id, SadmDossierMedicalOM rv) {
        return dossierMedicalOMRepository.findById(id)
                .map(existingNote -> {
                    existingNote.setComCwhoUpdate(rv.getComCwhoUpdate());
                    existingNote.setComXwhenUpdate(rv.getComXwhenUpdate());
                    return dossierMedicalOMRepository.save(existingNote);
                })
                .orElse(null);
    }

    private SadmDossierMedicalOMResponse mapToCarResponse(SadmDossierMedicalOM Dossier, Patients[] pateints) {
        Patients foundPatient = Arrays.stream(pateints)
                .filter(patient -> patient.getPlptSFKPatient().equals(Dossier.getPldmSFKPatients()))
                .findFirst ()
                .orElse(null);




        return SadmDossierMedicalOMResponse. builder ()
                .pldmSFKDossierMedical(Dossier.getPldmSFKDossierMedical())
                .pldmCAllergies(Dossier.getPldmCAllergies())
                .pldmCAntecedentsMedicaux(Dossier.getPldmCAntecedentsMedicaux())
                .pldmCMedicamentActuels(Dossier.getPldmCMedicamentActuels())
                .pldmXDateCreation(Dossier.getPldmXDateCreation())
                .comCwhoCreate(Dossier.getComCwhoCreate())
                .comCwhoUpdate(Dossier.getComCwhoUpdate())
                .comXwhenUpdate(Dossier.getComXwhenUpdate())
                .comXwhenCreate(Dossier.getComXwhenCreate())
                .comSdesactive(Dossier.getComSdesactive())
                .pldmSFKPatients(foundPatient)
                . build();
    }
}
