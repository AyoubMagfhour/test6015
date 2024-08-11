package com.chat.mot_cle.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Historique {

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

}
