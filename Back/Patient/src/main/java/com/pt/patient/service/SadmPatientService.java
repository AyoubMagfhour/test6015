package com.pt.patient.service;

import com.pt.patient.jpa.SadmPatientOM;
import com.pt.patient.models.SadmPatientOMResponse;

import java.util.List;

public interface SadmPatientService {
    SadmPatientOM savePatient(SadmPatientOM patient);
    SadmPatientOM updatePatient(SadmPatientOM patient);
    void deletePatient(Long id,String jwtToken);
    SadmPatientOMResponse getPatientById(Long id,String jwtToken);
    List<SadmPatientOMResponse> getAllPatients(String jwtToken);
    SadmPatientOMResponse findpatientbyface(float[] face , String jwtToken);
}
