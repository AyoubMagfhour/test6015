package com.pt.patient.models;

import com.pt.patient.jpa.Clinique;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SadmPatientOMResponse {

    private Long plptSFKPatient;
    private Long plptSfkinstance;
    private String plptCnom;
    private String plptCprenom;
    private String plptCAdress;
    private String plptCEmail;
    private String plptCTelephone;
    private String plplCnumeroSecuriteSociale;
    private String plptContactUrgenceNom;
    private String plptContactUrgenceTelephone;
    private byte[] plptBImage;
    private Date plptXDateNaissance;
    private float[] plptVisageDescription;
    private Clinique plptSfkclinique;
    private String comCwhoCreate;
    private Date comXwhenCreate;
    private String comCwhoUpdate;
    private Date comXwhenUpdate;
    private Integer comSdesactive;
}
