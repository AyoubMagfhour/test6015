package com.dm.dossier_medical.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SadmDossierMedicalOMDTO {

    private Long pldmSFKDossierMedical;
    private Date pldmXDateCreation;
    private String pldmCAntecedentsMedicaux;
    private String pldmCAllergies;
    private String pldmCMedicamentActuels;
    private String pldmCNoteClinique;
    private Long pldmSFKPatients;
    private String comCwhoCreate;
    private Date comXwhenCreate;
    private String comCwhoUpdate;
    private Date comXwhenUpdate;
    private Integer comSdesactive;
}
