package com.chat.chat_groupe.jpa;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Docteur {

    private Long pldtSpkdocteur;
    private Long pldtSfkinstance;
    private String pldtCemail;
    private String pldtCnom;
    private String pldtCpassword;
    private String pldtCprenom;
}
