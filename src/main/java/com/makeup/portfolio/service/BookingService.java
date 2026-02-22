package com.makeup.portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.makeup.portfolio.model.Utente;
import com.makeup.portfolio.repository.UtenteRepository;

@Service
public class BookingService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UtenteRepository utenteRepository;

    public void sendBookingEmail(String customerName, String customerEmail, String serviceTitle, String date) {
        Utente admin = utenteRepository.findByRole("ADMIN")
                .orElseThrow(() -> new RuntimeException("Errore: Nessun amministratore trovato nel database!"));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lucaalessio95@gmail.com"); // crea una email specifica per il sito
                                                    // [info.makeup.portfolio@gmail.com ] mittente
        message.setTo(admin.getEmail()); // email dove ricevere gli avvisi
        message.setSubject("Nuova Prenotazione: " + serviceTitle);
        message.setText("Hai ricevuto una nuova richiesta di prenotazione!\n\n" +
                "Servizio: " + serviceTitle + "\n" +
                "Cliente: " + customerName + "\n" +
                "Data: " + date + "\n" +
                "Email Cliente: " + customerEmail + "\n" +
                "Contatta il cliente per confermare.");

        emailSender.send(message);
    }
}
