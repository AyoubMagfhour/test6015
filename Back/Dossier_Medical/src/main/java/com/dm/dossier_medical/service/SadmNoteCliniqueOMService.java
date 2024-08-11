package com.dm.dossier_medical.service;

import com.dm.dossier_medical.jpa.SadmNoteCliniqueDTO;
import com.dm.dossier_medical.jpa.SadmNoteCliniqueOM;
import com.dm.dossier_medical.models.SadmNoteCliniqueOMResponse;

import java.util.List;

public interface SadmNoteCliniqueOMService {

    SadmNoteCliniqueOM savenoteclinique(SadmNoteCliniqueDTO sadmNoteClinique);

    List<SadmNoteCliniqueOMResponse> getallSadmNoteClinique(String jwtToken);

    SadmNoteCliniqueOMResponse getSadmNoteClinique(Long id,String jwtToken);

    void deleteSadmNoteClinique(Long id);

    List<SadmNoteCliniqueOMResponse> getNotecliniquebyDossierId(Long id,String jwtToken);

    SadmNoteCliniqueOM UpdateNoteclinique(Long id , SadmNoteCliniqueOM sadmNoteClinique);

}
