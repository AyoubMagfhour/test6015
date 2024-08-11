package com.pt.rendez_vous.jpa;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "sadm_rendez_vous", schema = "schpgmv21sad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SadmRendezVousOM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plrv_spkrendez_vous")
    private Long plrvSpkRendezVous;

    @Column(name = "plrv_sfkinstance")
    private Long plrvSfkinstance;

    @Column(name = "plrv_xdate_rendez_vous")
    private Date plrvXDateRendezVous;

    @Column(name = "plrv_xdate_end_rendez_vous")
    private Date plrvXDateendRendezVous;

    @Column(name = "plrv_cstatus",columnDefinition = "TEXT")
    private String plrvCStatus;

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

    @Column(name = "plrv_sfkclinique")
    private Long plrvSfkClinique;

    @Column(name = "plrv_sfkpatient")
    private Long plrvSfkPatient;
}
