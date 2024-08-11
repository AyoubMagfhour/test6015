package com.chat.historique.models;


import com.chat.historique.jpa.Chat_groupe;
import com.chat.historique.jpa.Docteur;
import com.chat.historique.jpa.System;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SadmHistoriqueResponseOM {


    private Long plhqSpkhistorique;
    private Long plhqSfkinstance;
    private byte[] plhqBlimage;
    private Boolean plhqCfavorite;
    private String plhqCquestion;
    private String plhqCreponse;
    private String plhqCtypePhoto;
    private Date plhqXdateRequest;
    private Date plhqXdateEnregistrementFavorite;
    private String comCwhoCreate;
    private Date comXwhenCreate;
    private String comCwhoUpdate;
    private Date comXwhenUpdate;
    private Integer comSdesactive;
    private Chat_groupe plhqSfkChatGroupe;
    private Docteur plhqSfkDocteur;
    private System plhqSfkSystem;
}
