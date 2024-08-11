package com.cq.clinique.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sadm_clinique", schema = "schpgmv21sad")
public class SadmCliniqueOM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plcq_spkclinique")
        private Long plcqSpkClinique;

    @Column(name = "plcq_sfkinstance")
    private Long plcqSfkinstance;

    @Column(name = "plcq_cnom_clinique",columnDefinition = "TEXT")
    private String plcqSfkCNomClinique;

    @Column(name = "plcq_caddress",columnDefinition = "TEXT")
    private String plcqCAddress;

    @Column(name = "plcq_ctelephone",columnDefinition = "TEXT")
    private String plcqCTelephone;

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
