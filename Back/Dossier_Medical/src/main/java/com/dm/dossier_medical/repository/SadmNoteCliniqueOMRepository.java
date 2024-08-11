package com.dm.dossier_medical.repository;

import com.dm.dossier_medical.jpa.SadmNoteCliniqueOM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SadmNoteCliniqueOMRepository extends JpaRepository<SadmNoteCliniqueOM , Long> {

    @Query("SELECT n FROM SadmNoteCliniqueOM n WHERE n.plncSFKDossier_Medical.pldmSFKDossierMedical = :id")
    List<SadmNoteCliniqueOM> findByDossierMedicalId(Long id);}
