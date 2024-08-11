package com.chat.historique.service;

import com.chat.historique.jpa.SadmHistoriqueOM;
import com.chat.historique.models.SadmHistoriqueResponseOM;

import java.util.Date;
import java.util.List;

public interface SadmHistoriqueOMService {

    SadmHistoriqueOM saveHistorique(SadmHistoriqueOM historique);

    SadmHistoriqueResponseOM getHistoriqueById(Long id,String jwtToken);

    List<SadmHistoriqueResponseOM> getAllHistorique(String jwtToken);

    void deleteHistorique(Long id);

    String toggleFavorite(Long id, Date Enregistrement);

    List<SadmHistoriqueResponseOM> searchByKeywordInResponse(String keyword , Long docteurID, String jwtToken);

    List<SadmHistoriqueResponseOM> getHistoriqueByDoctorEmail(Long docteurID, String jwtToken);

    List<SadmHistoriqueResponseOM> searchByReponse(String plhqCReponse, String jwtToken);

//    List<SadmHistoriqueResponseOM> findAllChatbyTitre(String titre);

    List<SadmHistoriqueResponseOM> findFavoriteHistoriques(String jwtToken);

    List<SadmHistoriqueResponseOM> findFavoriteHistoriquesbyDocteurEmail(Long docteurID,String jwtToken);

    List<SadmHistoriqueResponseOM> findAllChatbyId(Long id,String jwtToken);

    SadmHistoriqueResponseOM getLastEnregistrement(String jwtToken);

//    SadmHistoriqueOM updateHistoriqueById(Long historiqueId, SadmHistoriqueOM historique);

    void deleteAllHistoriquesByChatGroupe(Long chatGroupeId);


}

