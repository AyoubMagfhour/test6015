package com.pt.patient.serviceimpl;

import com.pt.patient.jpa.Clinique;
import com.pt.patient.jpa.SadmPatientOM;
import com.pt.patient.models.SadmPatientOMResponse;
import com.pt.patient.repository.SadmPatientOMRepository;
import com.pt.patient.service.SadmPatientService;
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
public class SadmPatientServiceImpl implements SadmPatientService {

    @Autowired
    private SadmPatientOMRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private final String CLINIQUE_URL = "http://localhost:8888/SERVICE-CLINIQUE/clinique/";

    private final String RENDEZ_URL = "http://localhost:8888/SERVICE-RENDEZ-VOUS/render_vous/";

    private static final double THRESHOLD = 0.6;
    @Override
    public SadmPatientOM savePatient(SadmPatientOM patient) {
        return repository.save(patient);
    }

    @Override
    public SadmPatientOM updatePatient(SadmPatientOM patient) {
        return repository.save(patient);
    }

    @Override
    public void deletePatient(Long id,String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        SadmPatientOM p = repository.findById(id).orElse(null);

        if (p != null) {
            repository.delete(p);
            ResponseEntity<Void> responseclinique = restTemplate.exchange(
                    this.RENDEZ_URL  + "patient/" + p.getPlptSFKPatient(),
                    HttpMethod.DELETE,
                    entity,
                    Void.class
            );
        }

    }

    @Override
    public SadmPatientOMResponse getPatientById(Long id,String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        SadmPatientOM patients = repository.findById(id).orElse(null);
        ResponseEntity<Clinique> responseclinique = restTemplate.exchange(
                this.CLINIQUE_URL   + patients.getPlptSfkclinique(),
                HttpMethod.GET,
                entity,
                Clinique.class
        );
        Clinique clinique = responseclinique.getBody();
        return SadmPatientOMResponse. builder ()
                .plptSFKPatient(patients.getPlptSFKPatient())
                .plptSfkinstance(patients.getPlptSfkinstance())
                .plptCnom(patients.getPlptCnom())
                .plptCprenom(patients.getPlptCprenom())
                .plptCAdress(patients.getPlptCAdress())
                .plptCEmail(patients.getPlptCEmail())
                .plptCTelephone(patients.getPlptCTelephone())
                .plplCnumeroSecuriteSociale(patients.getPlplCnumeroSecuriteSociale())
                .plptContactUrgenceNom(patients.getPlptContactUrgenceNom())
                .plptContactUrgenceTelephone(patients.getPlptContactUrgenceTelephone())
                .plptBImage(patients.getPlptBImage())
                .plptXDateNaissance(patients.getPlptXDateNaissance())
                .plptVisageDescription(patients.getPlptVisageDescription())
                .plptSfkclinique(clinique)
                . build();

    }

    @Override
    public List<SadmPatientOMResponse> getAllPatients(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        List<SadmPatientOM> patinets = repository.findAll();

        ResponseEntity<Clinique[]> cliniques = restTemplate.exchange(
                this.CLINIQUE_URL +"all",
                HttpMethod.GET,
                entity,
                Clinique[].class
        );
        Clinique[] cliniques1 = cliniques.getBody();
        return patinets.stream().map((SadmPatientOM pt) ->
                mapToCarResponse(pt,cliniques1)).toList();
    }

    @Override
    public SadmPatientOMResponse findpatientbyface(float[] face, String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<SadmPatientOM> utilisateurs = repository.findAll();

        for (SadmPatientOM utilisateur : utilisateurs) {
            if (utilisateur.getPlptVisageDescription() != null) {
                ResponseEntity<Clinique> responseclinique = restTemplate.exchange(
                        this.CLINIQUE_URL   + utilisateur.getPlptSfkclinique(),
                        HttpMethod.GET,
                        entity,
                        Clinique.class
                );
                Clinique clinique = responseclinique.getBody();
                double distance = computeEuclideanDistance(face, utilisateur.getPlptVisageDescription());
                if (distance < THRESHOLD) {
                    return SadmPatientOMResponse. builder ()
                            .plptSFKPatient(utilisateur.getPlptSFKPatient())
                            .plptSfkinstance(utilisateur.getPlptSfkinstance())
                            .plptCnom(utilisateur.getPlptCnom())
                            .plptCprenom(utilisateur.getPlptCprenom())
                            .plptCAdress(utilisateur.getPlptCAdress())
                            .plptCEmail(utilisateur.getPlptCEmail())
                            .plptCTelephone(utilisateur.getPlptCTelephone())
                            .plplCnumeroSecuriteSociale(utilisateur.getPlplCnumeroSecuriteSociale())
                            .plptContactUrgenceNom(utilisateur.getPlptContactUrgenceNom())
                            .plptContactUrgenceTelephone(utilisateur.getPlptContactUrgenceTelephone())
                            .plptBImage(utilisateur.getPlptBImage())
                            .plptXDateNaissance(utilisateur.getPlptXDateNaissance())
                            .plptVisageDescription(utilisateur.getPlptVisageDescription())
                            .plptSfkclinique(clinique)
                            . build();
                }
            }
        }
        return null;
    }

    private double computeEuclideanDistance(float[] a, float[] b) {
        if (a.length != b.length) throw new IllegalArgumentException("Arrays must have the same length");
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(sum);
    }

    private SadmPatientOMResponse mapToCarResponse(SadmPatientOM patientOM, Clinique[] clinique) {

        Clinique foundClinique = Arrays.stream(clinique)
                .filter(cli -> cli.getPlcqSpkClinique().equals(patientOM.getPlptSfkclinique()))
                .findFirst ()
                .orElse(null);

        return SadmPatientOMResponse. builder ()
                .plptSFKPatient(patientOM.getPlptSFKPatient())
                .plptSfkinstance(patientOM.getPlptSfkinstance())
                .plptCnom(patientOM.getPlptCnom())
                .plptCprenom(patientOM.getPlptCprenom())
                .plptCAdress(patientOM.getPlptCAdress())
                .plptCEmail(patientOM.getPlptCEmail())
                .plptCTelephone(patientOM.getPlptCTelephone())
                .plplCnumeroSecuriteSociale(patientOM.getPlplCnumeroSecuriteSociale())
                .plptContactUrgenceNom(patientOM.getPlptContactUrgenceNom())
                .plptContactUrgenceTelephone(patientOM.getPlptContactUrgenceTelephone())
                .plptBImage(patientOM.getPlptBImage())
                .plptXDateNaissance(patientOM.getPlptXDateNaissance())
                .plptVisageDescription(patientOM.getPlptVisageDescription())
                .plptSfkclinique(foundClinique)
                . build();
    }
}
