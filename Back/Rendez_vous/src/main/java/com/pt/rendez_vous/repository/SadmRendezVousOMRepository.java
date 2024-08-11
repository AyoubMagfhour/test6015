package com.pt.rendez_vous.repository;

import com.pt.rendez_vous.jpa.SadmRendezVousOM;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Repository
public interface SadmRendezVousOMRepository extends JpaRepository<SadmRendezVousOM, Long> {
    List<SadmRendezVousOM> findSadmRendezVousOMByPlrvSfkPatient(Long id);

    List<SadmRendezVousOM> findByPlrvXDateRendezVousBetween(Date startDate, Date endDate);

    @Query("SELECT r FROM SadmRendezVousOM r WHERE (r.plrvXDateRendezVous < :endDate AND r.plrvXDateendRendezVous > :startDate)")
    List<SadmRendezVousOM> findOverlappingAppointments(@Param("startDate") Date startDate,
                                                       @Param("endDate") Date endDate);

    // Query to find the next appointment
    @Query(value = "SELECT * FROM schpgmv21sad.sadm_rendez_vous WHERE plrv_xdate_rendez_vous > CURRENT_TIMESTAMP AND plrv_sfkclinique = :idclinique ORDER BY plrv_xdate_rendez_vous ASC", nativeQuery = true)
    List<SadmRendezVousOM> findNextAppointment(@Param("idclinique") Long idclinique);

    @Query("SELECT COUNT(r) FROM SadmRendezVousOM r WHERE r.plrvSfkClinique = :idclinique AND r.plrvXDateRendezVous >= :startOfDay AND r.plrvXDateRendezVous < :endOfDay")
    long countAppointmentsForCurrentDay(@Param("idclinique") Long idclinique,
                                        @Param("startOfDay") Date startOfDay,
                                        @Param("endOfDay") Date endOfDay);

    @Query("SELECT COUNT(r) FROM SadmRendezVousOM r WHERE r.plrvSfkClinique = :idclinique AND r.plrvXDateRendezVous >= :startOfDay AND r.plrvXDateRendezVous < :endOfDay AND r.plrvCStatus = 'In Progress'")
    long countAppointmentsForCurrentDayencours(@Param("idclinique") Long idclinique,
                                        @Param("startOfDay") Date startOfDay,
                                        @Param("endOfDay") Date endOfDay);

    @Query("SELECT COUNT(r) FROM SadmRendezVousOM r WHERE r.plrvSfkClinique = :idclinique AND r.plrvXDateRendezVous >= :startOfDay AND r.plrvXDateRendezVous < :endOfDay AND r.plrvCStatus = 'END'")
    long countAppointmentsForCurrentDayend(@Param("idclinique") Long idclinique,
                                               @Param("startOfDay") Date startOfDay,
                                               @Param("endOfDay") Date endOfDay);

    List<SadmRendezVousOM> findAllByPlrvSfkClinique(Long idclinique);
}
