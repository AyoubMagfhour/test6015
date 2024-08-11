package com.pt.rendez_vous.serviceimpl;

import com.pt.rendez_vous.jpa.Clinique;
import com.pt.rendez_vous.jpa.Patient;
import com.pt.rendez_vous.jpa.SadmRendezVousOM;
import com.pt.rendez_vous.repository.SadmRendezVousOMRepository;
import com.pt.rendez_vous.service.SadmRendezVousOMService;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class SmsReminderService {

    @Autowired
    private SadmRendezVousOMRepository rendezVousRepository;

    @Autowired
    private SadmRendezVousOMService service;

    private final VonageClient client;

    private final String PATIENT_URL = "http://localhost:8888/SERVICE-PATIENT/api/patients";
    private final String CLINIQUE_URL = "http://localhost:8888/SERVICE-CLINIQUE/clinique/";


    @Autowired
    private RestTemplate restTemplate;


    public SmsReminderService() {
        client = VonageClient.builder().apiKey("8e1c2e35").apiSecret("0yCSt5MBkWBTDfc4").build();
    }

    @Scheduled(cron = "0 51 16 * * *") // Runs every day at 3:50 PM
    public void sendReminders() {
        LocalDateTime startOfDay = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        Date startDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

        List<SadmRendezVousOM> appointments = rendezVousRepository.findByPlrvXDateRendezVousBetween(startDate, endDate);

        for (SadmRendezVousOM appointment : appointments) {
            //sendSmsReminder(appointment);
            sendreminderemail(appointment);
        }
    }

    @Scheduled(fixedRate = 60000) // Runs every minute
    public void updateAppointmentStatus() {
        List<SadmRendezVousOM> appointments = rendezVousRepository.findAll();
        Date currentDate = new Date();

        for (SadmRendezVousOM appointment : appointments) {
            if (currentDate.after(appointment.getPlrvXDateRendezVous()) &&
                    currentDate.before(appointment.getPlrvXDateendRendezVous())) {
                // Update the status to "In Progress" or any other status you want
                appointment.setPlrvCStatus("In Progress");
                rendezVousRepository.save(appointment); // Save changes to the database
            }
        }
    }

    @Scheduled(fixedRate = 60000) // Runs every minute
    public void updateAppointmentStatus2() {
        List<SadmRendezVousOM> appointments = rendezVousRepository.findAll();
        Date currentDate = new Date();

        for (SadmRendezVousOM appointment : appointments) {
            if (currentDate.after(appointment.getPlrvXDateendRendezVous())) {
                // Update the status to "In Progress" or any other status you want
                appointment.setPlrvCStatus("END");
                rendezVousRepository.save(appointment); // Save changes to the database
            }
        }
    }

    private void sendSmsReminder(SadmRendezVousOM appointment) {
        String patientPhoneNumber = "212" + getPhoneNumberForPatient(appointment.getPlrvSfkPatient()); // Assuming patient phone number is stored in this field
        LocalDateTime appointmentDateTime = appointment.getPlrvXDateRendezVous().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        String appointmentDate = appointmentDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String appointmentTime = appointmentDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        String messageText = "Rappel : Vous avez un rendez-vous demain, le " + appointmentDate + " à " + appointmentTime + ". Merci de confirmer votre présence.";

        TextMessage message = new TextMessage(getcliniquename(appointment.getPlrvSfkClinique()), patientPhoneNumber, messageText);
        SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);


        if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
            System.out.println("Message sent successfully.");
        } else {
            System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
        }
    }


    private void sendreminderemail(SadmRendezVousOM appointment){
        LocalDateTime appointmentDateTime = appointment.getPlrvXDateRendezVous().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        String appointmentDate = appointmentDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String appointmentTime = appointmentDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        service.sendreminderemail(getemailForPatient(appointment.getPlrvSfkPatient()),getcliniquename(appointment.getPlrvSfkClinique()) ,appointmentDate,appointmentTime );

    }

    private String getPhoneNumberForPatient(Long patientId) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Patient> response = restTemplate.exchange(
                this.PATIENT_URL + "/"  + patientId,
                HttpMethod.GET,
                entity,
                Patient.class
        );
        Patient patient = response.getBody();
        return patient.getPlptCTelephone(); // Replace with actual implementation
    }

    private String getemailForPatient(Long patientId) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Patient> response = restTemplate.exchange(
                this.PATIENT_URL + "/"  + patientId,
                HttpMethod.GET,
                entity,
                Patient.class
        );
        Patient patient = response.getBody();
        return patient.getPlptCEmail(); // Replace with actual implementation
    }

    private String getcliniquename(Long cliniqueid) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Clinique> response = restTemplate.exchange(
                this.CLINIQUE_URL + "/"  + cliniqueid,
                HttpMethod.GET,
                entity,
                Clinique.class
        );
        Clinique clinique = response.getBody();
        return clinique.getPlcqSfkCNomClinique(); // Replace with actual implementation
    }
}
