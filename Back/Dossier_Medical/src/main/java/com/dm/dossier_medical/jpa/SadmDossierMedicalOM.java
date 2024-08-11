package com.dm.dossier_medical.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sadm_dossier_medical", schema = "schpgmv21sad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SadmDossierMedicalOM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pldmSPKDossier_Medical")
    private Long pldmSFKDossierMedical;

    @Column(name = "pldmXDate_creation")
    private Date pldmXDateCreation;

    @Column(name = "pldmCAntecedents_medicaux" ,columnDefinition = "TEXT")
    private String pldmCAntecedentsMedicaux;

    @Column(name = "pldmCAllergies" ,columnDefinition = "TEXT")
    private String pldmCAllergies;

    @Column(name = "pldmCMedicament_Actuels" ,columnDefinition = "TEXT")
    private String pldmCMedicamentActuels;

    @Column(name = "pldmSFKPatients")
    private Long pldmSFKPatients;

    @OneToMany(mappedBy = "plncSFKDossier_Medical", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SadmNoteCliniqueOM> nc = new ArrayList<>();

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
