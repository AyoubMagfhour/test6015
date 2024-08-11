package com.dm.dossier_medical.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patients {

    private Long plptSFKPatient;
    private Long plptSfkinstance;
    private String plptCnom;
    private String plptCprenom;
    private String plptCAdress;
    private String plptCTelephone;
    private String plplCnumeroSecuriteSociale;
    private String plptContactUrgenceNom;
    private String plptContactUrgenceTelephone;
    private byte[] plptBImage;
    private Date plptXDateNaissance;
    private float[] plptVisageDescription;
}
