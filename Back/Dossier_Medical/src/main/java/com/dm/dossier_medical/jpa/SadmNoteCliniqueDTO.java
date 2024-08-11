package com.dm.dossier_medical.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SadmNoteCliniqueDTO {
    private Long plncSPKNoteClinique;
    private String plncCNote;
    private Long plncSFKDossier_Medical;
    private Long plncSFKDocteur;
    private byte[] plncBImage;
    private String comCwhoCreate;
    private Date comXwhenCreate;
    private String comCwhoUpdate;
    private Date comXwhenUpdate;
    private Integer comSdesactive;
}
