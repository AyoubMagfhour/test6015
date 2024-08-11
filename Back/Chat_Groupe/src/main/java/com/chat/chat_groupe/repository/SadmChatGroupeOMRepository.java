package com.chat.chat_groupe.repository;

import com.chat.chat_groupe.jpa.SadmChatGroupeOM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SadmChatGroupeOMRepository extends JpaRepository<SadmChatGroupeOM, Long> {

    // Recherche un groupe de chat par titre
    SadmChatGroupeOM findByPlgcCtitre(String plgcTitre);

    // Recherche les groupes de chat associés à un médecin spécifique qui ne sont pas archivés
    @Query("SELECT c FROM SadmChatGroupeOM c WHERE c.plgcSFKDocteur = :id and c.plgcBarchive = false ORDER BY c.plgcSpkchatGroupe DESC")
    List<SadmChatGroupeOM> findByDocteurId(Long id);

    // Recherche les groupes de chat archivés associés à un médecin spécifique qui sont archivés
    @Query("SELECT c FROM SadmChatGroupeOM c WHERE c.plgcSFKDocteur = :id and c.plgcBarchive = true")
    List<SadmChatGroupeOM> findByPlgcBarchive(Long id);

}