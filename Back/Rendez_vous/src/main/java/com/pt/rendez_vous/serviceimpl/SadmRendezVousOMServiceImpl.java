package com.pt.rendez_vous.serviceimpl;


import com.pt.rendez_vous.jpa.Clinique;
import com.pt.rendez_vous.jpa.Patient;
import com.pt.rendez_vous.jpa.SadmRendezVousOM;
import com.pt.rendez_vous.models.SadmRendezVousOMResponse;
import com.pt.rendez_vous.repository.SadmRendezVousOMRepository;
import com.pt.rendez_vous.service.SadmRendezVousOMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.util.Properties;


@Service
public class SadmRendezVousOMServiceImpl implements SadmRendezVousOMService {

    @Autowired
    private SadmRendezVousOMRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private final String CLINIQUE_URL = "http://localhost:8888/SERVICE-CLINIQUE/clinique/";
    private final String PATIENT_URL = "http://localhost:8888/SERVICE-PATIENT/api/patients";

    @Override
    public SadmRendezVousOM prendRendezVous(SadmRendezVousOM rv) {
        return repository.save(rv);
    }

    @Override
    public List<SadmRendezVousOMResponse> getALLSadmRendezVous(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<SadmRendezVousOM> rendezVousOMS = repository.findAll();

        ResponseEntity<Patient[]> patient = restTemplate.exchange(
                this.PATIENT_URL,
                HttpMethod.GET,
                entity,
                Patient[].class
        );
        Patient[] patients = patient.getBody();

        ResponseEntity<Clinique[]> cliniques = restTemplate.exchange(
                this.CLINIQUE_URL +"all",
                HttpMethod.GET,
                entity,
                Clinique[].class
        );
        Clinique[] cliniques1 = cliniques.getBody();

        return rendezVousOMS.stream().map((SadmRendezVousOM rv) ->
                mapToCarResponse(rv, patients,cliniques1)).toList();
    }

    @Override
    public SadmRendezVousOMResponse getSadmRendezVous(Long id , String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        SadmRendezVousOM re = repository.findById(id).orElse(null);

        ResponseEntity<Patient> response = restTemplate.exchange(
                this.PATIENT_URL + "/"  + re.getPlrvSfkPatient(),
                HttpMethod.GET,
                entity,
                Patient.class
        );
        Patient patient = response.getBody();

        ResponseEntity<Clinique> responseclinique = restTemplate.exchange(
                this.CLINIQUE_URL   + re.getPlrvSfkClinique(),
                HttpMethod.GET,
                entity,
                Clinique.class
        );
        Clinique clinique = responseclinique.getBody();

        return  SadmRendezVousOMResponse.builder()
                .plrvSpkRendezVous(re.getPlrvSpkRendezVous())
                .plrvSfkinstance(re.getPlrvSfkinstance())
                .plrvXDateRendezVous(re.getPlrvXDateRendezVous())
                .plrvXDateendRendezVous(re.getPlrvXDateendRendezVous())
                .plrvCStatus(re.getPlrvCStatus())
                .comCwhoCreate(re.getComCwhoCreate())
                .comCwhoUpdate(re.getComCwhoUpdate())
                .comXwhenCreate(re.getComXwhenCreate())
                .comXwhenUpdate(re.getComXwhenUpdate())
                .comSdesactive(re.getComSdesactive())
                .plrvSfkPatient(patient)
                .plrvSfkClinique(clinique)
                .build();
    }

    @Override
    public List<SadmRendezVousOMResponse> getAllbycliniqueid(Long id ,String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<SadmRendezVousOM> rendezVousOMS = repository.findAllByPlrvSfkClinique(id);

        ResponseEntity<Patient[]> patient = restTemplate.exchange(
                this.PATIENT_URL,
                HttpMethod.GET,
                entity,
                Patient[].class
        );
        Patient[] patients = patient.getBody();

        ResponseEntity<Clinique[]> cliniques = restTemplate.exchange(
                this.CLINIQUE_URL +"all",
                HttpMethod.GET,
                entity,
                Clinique[].class
        );
        Clinique[] cliniques1 = cliniques.getBody();

        return rendezVousOMS.stream().map((SadmRendezVousOM rv) ->
                mapToCarResponse(rv, patients,cliniques1)).toList();
    }

    @Override
    public void DeleteSadmRendezVous(Long id) {
        SadmRendezVousOM re = repository.findById(id).orElse(null);
        if(re != null) {
            repository.delete(re);
        }
    }

    @Override
    public void DeleteSadmRendezVousbyPatientid(Long id) {
        List<SadmRendezVousOM> re = repository.findSadmRendezVousOMByPlrvSfkPatient(id);
        if(re != null) {
            repository.deleteAll(re);
        }
    }

    @Override
    public List<SadmRendezVousOM> findOverlappingAppointments(Date startDate, Date endDate) {
        return repository.findOverlappingAppointments(startDate, endDate);
    }

    @Override
    public List<SadmRendezVousOMResponse> getNextAppointment(Long idclinique,String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<SadmRendezVousOM> rendezVousOMS = repository.findNextAppointment(idclinique);
        ResponseEntity<Patient[]> patient = restTemplate.exchange(
                this.PATIENT_URL,
                HttpMethod.GET,
                entity,
                Patient[].class
        );
        Patient[] patients = patient.getBody();

        ResponseEntity<Clinique[]> cliniques = restTemplate.exchange(
                this.CLINIQUE_URL +"all",
                HttpMethod.GET,
                entity,
                Clinique[].class
        );
        Clinique[] cliniques1 = cliniques.getBody();

        return rendezVousOMS.stream().map((SadmRendezVousOM rv) ->
                mapToCarResponse(rv, patients,cliniques1)).toList();

    }

    @Override
    public long countAppointmentsForCurrentDay(Long idclinique) {
        LocalDate today = LocalDate.now(ZoneId.of("Africa/Casablanca")); // Adjust time zone if needed
        Date startOfDay = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return repository.countAppointmentsForCurrentDay(idclinique, startOfDay, endOfDay);
    }

    @Override
    public long countAppointmentsForCurrentDayencours(Long idclinique) {
        LocalDate today = LocalDate.now(ZoneId.of("Africa/Casablanca")); // Adjust time zone if needed
        Date startOfDay = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return repository.countAppointmentsForCurrentDayencours(idclinique, startOfDay, endOfDay);
    }

    @Override
    public long countAppointmentsForCurrentDayend(Long idclinique) {
        LocalDate today = LocalDate.now(ZoneId.of("Africa/Casablanca")); // Adjust time zone if needed
        Date startOfDay = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return repository.countAppointmentsForCurrentDayend(idclinique, startOfDay, endOfDay);
    }

    @Override
    public void sendreminderemail(String email , String subject, String Date , String Heure) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Sender and recipient email addresses
        String senderEmail = "animeop96@gmail.com";
        String password = "jhgvcrhxsdckmwhf";

        // Create a session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        try {
            // Create a message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            if (email != null) {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            }else {
                System.out.println("error" );
            }
            message.setSubject(subject);

            // HTML content with Formation name
            String htmlContent = "<html><body>"
                    + "<div style='text-align: center;'>"
                    + "<span\n" +
                    "      style=\"font-size:53px;font-weight:bold;color:#2b4d78;text-decoration:underline;text-decoration-color:#5cc9b2;display:block\">Doc<span\n" +
                    "        style=\"color:#5cc9b2;font-size:53px\">X</span>ia </span>"
                    + "</div>"
                    + "<div style='margin-top: 20px;'>"
                    + "<h2>Ceci et un Rappel</h2>"
                    + "<p>Rappel : Vous avez un rendez-vous demain, le " + Date + " à " + Heure + ". Merci de confirmer votre présence.</p>"
                    + "</div>"
                    + "</body></html>";

            // Create a body part to hold the HTML content
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/html");

            // Create a multipart object to hold the HTML content
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);

            // Set the multipart as the content of the message
            message.setContent(multipart);

            // Send the message
            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            System.err.println("Failed to send the email. Reason: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private SadmRendezVousOMResponse mapToCarResponse(SadmRendezVousOM rendezVousOM, Patient[] pateints , Clinique[] clinique) {
        Patient foundPatient = Arrays.stream(pateints)
                .filter(patient -> patient.getPlptSFKPatient().equals(rendezVousOM.getPlrvSfkPatient()))
                .findFirst ()
                .orElse(null);

        Clinique foundClinique = Arrays.stream(clinique)
                .filter(cli -> cli.getPlcqSpkClinique().equals(rendezVousOM.getPlrvSfkClinique()))
                .findFirst ()
                .orElse(null);


        return SadmRendezVousOMResponse. builder ()
                .plrvSpkRendezVous(rendezVousOM.getPlrvSpkRendezVous())
                .plrvSfkinstance(rendezVousOM.getPlrvSfkinstance())
                .plrvXDateRendezVous(rendezVousOM.getPlrvXDateRendezVous())
                .plrvXDateendRendezVous(rendezVousOM.getPlrvXDateendRendezVous())
                .plrvCStatus(rendezVousOM.getPlrvCStatus())
                .comCwhoCreate(rendezVousOM.getComCwhoCreate())
                .comCwhoUpdate(rendezVousOM.getComCwhoUpdate())
                .comXwhenCreate(rendezVousOM.getComXwhenCreate())
                .comXwhenUpdate(rendezVousOM.getComXwhenUpdate())
                .comSdesactive(rendezVousOM.getComSdesactive())
                .plrvSfkPatient(foundPatient)
                .plrvSfkClinique(foundClinique)
                . build();
    }
}
