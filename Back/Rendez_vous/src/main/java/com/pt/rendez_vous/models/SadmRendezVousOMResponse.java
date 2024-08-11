package com.pt.rendez_vous.models;


import com.pt.rendez_vous.jpa.Clinique;
import com.pt.rendez_vous.jpa.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SadmRendezVousOMResponse {

    private Long plrvSpkRendezVous;
    private Long plrvSfkinstance;
    private Date plrvXDateRendezVous;
    private Date plrvXDateendRendezVous;
    private String plrvCStatus;
    private String comCwhoCreate;
    private Date comXwhenCreate;
    private String comCwhoUpdate;
    private Date comXwhenUpdate;
    private Integer comSdesactive;
    private Clinique plrvSfkClinique;
    private Patient plrvSfkPatient;
}
