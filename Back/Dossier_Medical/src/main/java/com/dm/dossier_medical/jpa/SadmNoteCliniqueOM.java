package com.dm.dossier_medical.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "sadm_note_clinique", schema = "schpgmv21sad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SadmNoteCliniqueOM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plncSPKNote_Clinique")
    private Long plncSPKNoteClinique;

    @Column(name = "plncCNote" ,columnDefinition = "TEXT")
    private String plncCNote;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plncSFKDossier_Medical")
    private SadmDossierMedicalOM plncSFKDossier_Medical;

    @Column(name = "plncSFKDocteur")
    private Long plncSFKDocteur;

    @Column(name="plnc_BImage")
    private byte[] plncBImage;


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
