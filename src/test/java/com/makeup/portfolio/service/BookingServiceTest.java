package com.makeup.portfolio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.makeup.portfolio.model.Utente;
import com.makeup.portfolio.repository.UtenteRepository;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {


    @Mock
    private JavaMailSender emailSender;

    @Mock
    private UtenteRepository utenteRepository;

    @InjectMocks
    private BookingService bookingService;

    private Utente adminFinto;

    @BeforeEach
    void setUp() {
        adminFinto = new Utente();
        adminFinto.setEmail("admin@test.com");
        adminFinto.setRole("ADMIN");
    }

    @Test
    @DisplayName("Ivia email quando l admin esiste")
    void testSendBookingEmail_Success() {

    }


    @Test
    void sendBookingEmail_ShouldSendEmailCorrectly_WhenAdminExists() {

        //ARRANGE
        String customerName = "Mario Rossi";
        String customerEmail = "mario@example.com";
        String serviceTitle = "Trucco Sposa";
        String date = "2024-06-20";

        when(utenteRepository.findByRole("ADMIN")).thenReturn(Optional.of(adminFinto));

        //ACT
        bookingService.sendBookingEmail(customerName, customerEmail, serviceTitle, date);


        //ASSERT
        //Verifico che il repo sia stato chiamata correttamente
        verify(utenteRepository, times(1)).findByRole("ADMIN");

        //Utilizzo argument capture per vedere il contenuto dell email inviata
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(emailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertEquals("admin@test.com", sentMessage.getTo()[0]);
        assertEquals("Nuova Prenotazione: Trucco Sposa", sentMessage.getSubject());
        assertEquals("lucaalessio95@gmail.com", sentMessage.getFrom());

        //Verifico che il corpo contenga i dati passati
        String body = sentMessage.getText();
        assert body != null;
        assert body.contains(customerName);
        assert body.contains(serviceTitle);
    }

    @Test
    void sendBookingEmail_ShouldThrowException_WhenAdminNotFound() {
        // Arrange
        when(utenteRepository.findByRole("ADMIN")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.sendBookingEmail("Name", "email@test.com", "Service", "2024-01-01");
        });

        assertEquals("Errore: Nessun amministratore trovato nel database!", exception.getMessage());
        
        // Verifichiamo che l'invio email non sia mai stato tentato
        verify(emailSender, times(0)).send(any(SimpleMailMessage.class));
    }
}
