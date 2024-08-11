package com.chat.historique.jpa;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat_groupe {

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
}
