package com.chat.mot_cle.models;


import com.chat.mot_cle.jpa.Historique;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SadmMotCleResponseOM {

    private Long plmcSpkmotCle;

    private Long plmcSfkinstance;

    private String plmcCmotCle;

    private String comCwhoCreate;

    private Date comXwhenCreate;

    private String comCwhoUpdate;

    private Date comXwhenUpdate;

    private Integer comSdesactive;

    private Historique Historique;
}
