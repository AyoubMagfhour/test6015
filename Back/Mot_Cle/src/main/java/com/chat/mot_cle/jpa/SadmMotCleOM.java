package com.chat.mot_cle.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="sadm_mot_cle", schema = "schpgmv21sad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SadmMotCleOM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plmc_spkmot_cle")
    private Long plmcSpkmotCle;

    @Column(name = "plmc_sfkinstance")
    private Long plmcSfkinstance;

    @Column(name = "plmc_cmot_cle",columnDefinition = "TEXT")
    private String plmcCmotCle;

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

    @Column(name = "plmc_spkhistorique")
    private Long plmcSpkhistorique;
}
