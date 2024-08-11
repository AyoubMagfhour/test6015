package com.chat.historique.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class System {


    private Long plstSpksystem;
    private Long plstSfkinstance;
    private String plstCdescriptionSystem;
    private String plstCnomSystem;
    private String comCwhoCreate;
    private Date comXwhenCreate;
    private String comCwhoUpdate;
    private Date comXwhenUpdate;
    private Integer comSdesactive;
}
