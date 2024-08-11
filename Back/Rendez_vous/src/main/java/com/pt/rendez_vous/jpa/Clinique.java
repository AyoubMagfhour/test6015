package com.pt.rendez_vous.jpa;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clinique {

    private Long plcqSpkClinique;
    private Long plcqSfkinstance;
    private String plcqSfkCNomClinique;
    private String plcqCAddress;
    private String plcqCTelephone;

}
