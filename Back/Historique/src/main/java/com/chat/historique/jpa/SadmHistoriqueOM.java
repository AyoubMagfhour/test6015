package com.chat.historique.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "sadm_historique", schema = "schpgmv21sad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SadmHistoriqueOM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plhq_spkhistorique")
    private Long plhqSpkhistorique;

    @Column(name = "plhq_sfkinstance")
    private Long plhqSfkinstance;

    @Column(name = "plhq_blimage")
    private byte[] plhqBlimage;

    @Column(name = "plhq_cfavorite")
    private Boolean plhqCfavorite;

    @Column(name = "plhq_cquestion",columnDefinition = "TEXT")
    private String plhqCquestion;

    @Column(name = "plhq_creponse",columnDefinition = "TEXT")
    private String plhqCreponse;

    @Column(name = "plhq_ctype_photo",columnDefinition = "TEXT")
    private String plhqCtypePhoto;

    @Column(name = "plhq_xdate_request")
    private Date plhqXdateRequest;

    @Column(name = "plhq_xdate_enregistrement_favorite")
    private Date plhqXdateEnregistrementFavorite;

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

    @Column(name = "plhq_sfkchatgroupe")
    private Long plhqSfkChatGroupe;

    @Column(name = "plhq_sfkdocteur")
    private Long plhqSfkDocteur;

    @Column(name = "plhq_sfksystem")
    private Long plhqSfkSystem;
}
