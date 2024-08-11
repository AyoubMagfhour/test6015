package com.cq.clinique.service;

import com.cq.clinique.jpa.SadmCliniqueOM;

import java.util.List;

public interface SadmCliniqueOMService {

    SadmCliniqueOM saveclinique(SadmCliniqueOM sadmClinique);
    SadmCliniqueOM findbyId(Long id);
    List<SadmCliniqueOM> findAll();
    void deleteById(Long id);
}
