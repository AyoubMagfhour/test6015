package com.chat.mot_cle.SadmMotCleOMService;

import com.chat.mot_cle.jpa.SadmMotCleOM;
import com.chat.mot_cle.models.SadmMotCleResponseOM;

import java.util.List;

public interface SadmMotCleOMService {

    SadmMotCleOM save(SadmMotCleOM sadmMotCle);
    List<SadmMotCleResponseOM> findAll();
    List<SadmMotCleResponseOM> findByKeyword(String keyword);
    SadmMotCleOM updateMotCleByHistoriqueId(Long historiqueId, String newMotCle);
    SadmMotCleResponseOM findByHistoriqueSPK(Long historiqueId);
    List<SadmMotCleResponseOM> getHistoriquebykeyWord(String keyword, String jwtToken);
    void deletemotclebyplmcSpkhistorique(Long id, String jwtToken);
}
