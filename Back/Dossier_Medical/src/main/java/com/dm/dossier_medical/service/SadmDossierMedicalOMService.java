package com.dm.dossier_medical.service;

import com.dm.dossier_medical.jpa.SadmDossierMedicalOM;
import com.dm.dossier_medical.models.SadmDossierMedicalOMResponse;
import com.dm.dossier_medical.repository.SadmDossierMedicalOMRepository;

import java.util.List;

public interface SadmDossierMedicalOMService {

    SadmDossierMedicalOM AjouterDossier(SadmDossierMedicalOM rv);

    List<SadmDossierMedicalOMResponse> getALLDossier(String jwtToken);

    SadmDossierMedicalOMResponse getDossierparID(Long id , String jwtToken);

    SadmDossierMedicalOMResponse getDossierparpatientID(Long id , String jwtToken);

    void DeleteDossier(Long id);

    SadmDossierMedicalOM updateDossier(Long id ,SadmDossierMedicalOM rv);

    SadmDossierMedicalOM updateDossier2(Long id ,SadmDossierMedicalOM rv);
}
