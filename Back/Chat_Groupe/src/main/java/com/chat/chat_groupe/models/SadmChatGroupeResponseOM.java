package com.chat.chat_groupe.models;


import com.chat.chat_groupe.jpa.Docteur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SadmChatGroupeResponseOM {


    private Long plgcSpkchatGroupe;
    private Long plgcSfkinstance;
    private String plgcCtitre;
    private Date plgcXdateCreation;
    private Boolean plgcBarchive;
    private String comCwhoCreate;
    private Date comXwhenCreate;
    private String comCwhoUpdate;
    private Date comXwhenUpdate;
    private Integer comSdesactive;
    private Docteur docteur;
}
