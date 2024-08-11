package com.dm.dossier_medical.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Docteur {

    private Long pldtSpkdocteur;
    private Long pldtSfkinstance;
    private String pldtCemail;
    private String pldtCnom;
    private String pldtCpassword;
    private String pldtCprenom;
    private Clinique clinique;
}
