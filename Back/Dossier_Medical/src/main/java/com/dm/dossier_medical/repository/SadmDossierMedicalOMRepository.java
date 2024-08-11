package com.dm.dossier_medical.repository;

import com.dm.dossier_medical.jpa.SadmDossierMedicalOM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SadmDossierMedicalOMRepository extends JpaRepository<SadmDossierMedicalOM,Long> {

    SadmDossierMedicalOM findSadmDossierMedicalOMSByPldmSFKPatients(Long patientId);
}
