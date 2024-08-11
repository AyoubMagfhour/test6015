package com.pt.rendez_vous.jpa;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    private Long plptSFKPatient;
    private Long plptSfkinstance;
    private String plptCnom;
    private String plptCprenom;
    private String plptCAdress;
    private String plptCTelephone;
    private String plptCEmail;
    private String plplCnumeroSecuriteSociale;
    private String plptContactUrgenceNom;
    private String plptContactUrgenceTelephone;
    private byte[] plptBImage;
    private Date plptXDateNaissance;
    private float[] plptVisageDescription;
}
