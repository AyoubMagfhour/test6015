package com.pt.rendez_vous.service;

import com.pt.rendez_vous.jpa.SadmRendezVousOM;
import com.pt.rendez_vous.models.SadmRendezVousOMResponse;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SadmRendezVousOMService {

    SadmRendezVousOM prendRendezVous(SadmRendezVousOM rv);

    List<SadmRendezVousOMResponse> getALLSadmRendezVous(String jwtToken);

    SadmRendezVousOMResponse getSadmRendezVous(Long id , String jwtToken);

    List<SadmRendezVousOMResponse> getAllbycliniqueid(Long id,String jwtToken);

    void DeleteSadmRendezVous(Long id);

    void DeleteSadmRendezVousbyPatientid(Long id);

    List<SadmRendezVousOM> findOverlappingAppointments(Date startDate, Date endDate);

    List<SadmRendezVousOMResponse> getNextAppointment(Long idclinique ,String jwtToken);

    long countAppointmentsForCurrentDay(Long idclinique);

    long countAppointmentsForCurrentDayencours(Long idclinique);

    long countAppointmentsForCurrentDayend(Long idclinique);

    void sendreminderemail(String email , String subject, String Date , String Heure); ;


}
