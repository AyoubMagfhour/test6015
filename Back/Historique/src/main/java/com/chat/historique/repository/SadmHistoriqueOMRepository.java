package com.chat.historique.repository;

import com.chat.historique.jpa.SadmHistoriqueOM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SadmHistoriqueOMRepository extends JpaRepository<SadmHistoriqueOM, Long> {

    // Recherche d'historiques contenant un mot-clé dans la réponse et associés à un médecin spécifique
    @Query("SELECT h FROM SadmHistoriqueOM h WHERE h.plhqCreponse LIKE CONCAT('%', :keyword, '%') AND h.plhqSfkDocteur = :docteurID")
    List<SadmHistoriqueOM> findByKeywordAndDocteuremail(String keyword, Long docteurID);

    // Recherche d'historiques associés à un médecin spécifique
    @Query("SELECT h FROM SadmHistoriqueOM h WHERE h.plhqSfkDocteur = :docteurID")
    List<SadmHistoriqueOM> findByDocteuremail(Long docteurID);

    // Recherche d'historiques contenant une partie de la réponse
    List<SadmHistoriqueOM> findByPlhqCreponseContaining(String plhqCReponse);

    // Recherche d'historiques associés à un groupe de chat spécifique par son titre
//    List<SadmHistoriqueOM> findByChatgroupeTitre(String titre);

    // Recherche d'historiques associés à un groupe de chat spécifique par son ID
    List<SadmHistoriqueOM> findByplhqSfkChatGroupe(Long id);

    // Recherche d'historiques marqués comme favoris
    List<SadmHistoriqueOM> findByPlhqCfavoriteTrue();

    // Recherche d'historiques favoris d'un médecin spécifique   knte dayer by email khassni f front ndirha by id
    @Query("SELECT h FROM SadmHistoriqueOM h WHERE h.plhqSfkDocteur = :docteurID AND h.plhqCfavorite = true ORDER BY h.plhqXdateEnregistrementFavorite DESC")
    List<SadmHistoriqueOM> findByDocteurfavorite(Long docteurID);

    // Recherche du dernier enregistrement historique
    @Query(value = "SELECT * FROM schpgmv21sad.sadm_historique ORDER BY plhq_spkhistorique DESC LIMIT 1", nativeQuery = true)
    SadmHistoriqueOM findLastEnregistrement();

}
