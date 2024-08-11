package com.dm.dossier_medical.models;

import com.dm.dossier_medical.jpa.Patients;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SadmDossierMedicalOMResponse {

    private Long pldmSFKDossierMedical;
    private Date pldmXDateCreation;
    private String pldmCAntecedentsMedicaux;
    private String pldmCAllergies;
    private String pldmCMedicamentActuels;
    private String pldmCNoteClinique;
    private Patients pldmSFKPatients;
    private String comCwhoCreate;
    private Date comXwhenCreate;
    private String comCwhoUpdate;
    private Date comXwhenUpdate;
    private Integer comSdesactive;
}
