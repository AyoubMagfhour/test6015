package com.chat.mot_cle.repository;

import com.chat.mot_cle.jpa.SadmMotCleOM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SadmMotCleOMRepository extends JpaRepository<SadmMotCleOM, Long> {

    // Recherche des mots-clés par leur libellé
    List<SadmMotCleOM> findByPlmcCmotCle(String plmcCMotcle);

    // Recherche d'un mot-clé par l'ID de l'historique associé
    SadmMotCleOM findByPlmcSpkhistorique(Long historiqueId);


//    @Query("SELECT h FROM SadmMotCleOM h WHERE h.historique.chatGroupe.plgcSpkchatGroupe = :chatgroupeId")
//    List<SadmMotCleOM> findByHistoriquechatGroupe(Long chatgroupeId);

    // Recherche des mots-clés contenant un mot-clé spécifique
    List<SadmMotCleOM> findByPlmcCmotCleContaining(String keyword);

}
