package com.chat.chat_groupe.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "sadm_chat_groupe", schema = "schpgmv21sad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SadmChatGroupeOM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plgc_spkchat_groupe")
    private Long plgcSpkchatGroupe;

    @Column(name = "plgc_sfkinstance")
    private Long plgcSfkinstance;

    @Column(name = "plgc_ctitre" ,columnDefinition = "TEXT")
    private String plgcCtitre;

    @Column(name = "plgc_xdate_creation")
    private Date plgcXdateCreation;

    @Column(name = "plgc_barchive")
    private Boolean plgcBarchive;

    @Column(name = "com_cwho_create" ,columnDefinition = "TEXT")
    private String comCwhoCreate;

    @Column(name = "com_xwhen_create")
    private Date comXwhenCreate;

    @Column(name = "com_cwho_update",columnDefinition = "TEXT")
    private String comCwhoUpdate;

    @Column(name = "com_xwhen_update")
    private Date comXwhenUpdate;

    @Column(name = "com_sdesactive")
    private Integer comSdesactive;

    @Column(name = "plgc_sfkdocteur")
    private Long plgcSFKDocteur;
}
