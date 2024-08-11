package com.system.system.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sadm_system", schema = "schpgmv21sad")
public class SadmSystemOM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plst_spksystem")
    private Long plstSpksystem;

    @Column(name = "plst_sfkinstance")
    private Long plstSfkinstance;

    @Column(name = "plst_cdescription_system",columnDefinition = "TEXT")
    private String plstCdescriptionSystem;

    @Column(name = "plst_cnom_system",columnDefinition = "TEXT")
    private String plstCnomSystem;

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
