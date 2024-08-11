package com.pt.patient.jpa;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sadm_patient", schema = "schpgmv21sad")
public class SadmPatientOM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plpt_sfkpatient")
    private Long plptSFKPatient;

    @Column(name = "plpt_sfkinstance")
    private Long plptSfkinstance;

    @Column(name = "plpt_cnom",columnDefinition = "TEXT")
    private String plptCnom;

    @Column(name = "plpt_cprenom",columnDefinition = "TEXT")
    private String plptCprenom;

    @Column(name = "plpt_cadress",columnDefinition = "TEXT")
    private String plptCAdress;

    @Column(name = "plpt_cemail",columnDefinition = "TEXT")
    private String plptCEmail;

    @Column(name = "plpt_ctelephone",columnDefinition = "TEXT")
    private String plptCTelephone;

    @Column(name= "plpl_cnumero_securite_sociale")
    private String plplCnumeroSecuriteSociale;

    @Column(name="plpt_contact_urgence_nom")
    private String plptContactUrgenceNom;

    @Column(name="plpt_contact_urgence_telephone")
    private String plptContactUrgenceTelephone;

    @Column(name="plpt_BImage")
    private byte[] plptBImage;

    @Column(name="plpt_XDate_Naissance")
    private Date plptXDateNaissance;

    @Column(name="plpt_visage_description")
    @JsonDeserialize(using = FloatArrayDeserializer.class)
    private float[] plptVisageDescription;

    @Column(name="plpt_sfkclinique")
    private Long plptSfkclinique;

    @Column(name = "com_cwho_create",columnDefinition = "TEXT")
    private String comCwhoCreate;

    @Column(name = "com_xwhen_create")
    private Date comXwhenCreate;

    @Column(name = "com_cwho_update",columnDefinition = "TEXT")
    private String comCwhoUpdate;

    @Column(name = "com_xwhen_update")
    private Date comXwhenUpdate;

    @Column(name = "com_sdesactive")
    private Integer comSdesactive;

}
