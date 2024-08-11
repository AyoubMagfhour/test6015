package com.dm.dossier_medical.models;

import com.dm.dossier_medical.jpa.Docteur;
import com.dm.dossier_medical.jpa.SadmDossierMedicalOM;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SadmNoteCliniqueOMResponse {

    private Long plncSPKNoteClinique;
    private String plncCNote;
    private SadmDossierMedicalOM plncSFKDossier_Medical;
    private Docteur plncSFKDocteur;
    private byte[] plncBImage;
    private String comCwhoCreate;
    private Date comXwhenCreate;
    private String comCwhoUpdate;
    private Date comXwhenUpdate;
    private Integer comSdesactive;
}
