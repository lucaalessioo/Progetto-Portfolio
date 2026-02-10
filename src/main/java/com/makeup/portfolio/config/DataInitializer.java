// package com.makeup.portfolio.config;

// import com.makeup.portfolio.model.Utente;
// import com.makeup.portfolio.repository.UtenteRepository;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// @Component
// public class DataInitializer implements CommandLineRunner {

//     private final UtenteRepository utenteRepository;
//     private final PasswordEncoder passwordEncoder;

//     public DataInitializer(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
//         this.utenteRepository = utenteRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         System.out.println(">>>> CONTROLLO CREAZIONE UTENTE ADMIN...");

//         String usernameAdmin = "federica_makeup";

//         if (utenteRepository.findByUsername(usernameAdmin).isEmpty()) {
//             System.out.println(">>>> UTENTE NON TROVATO. CREAZIONE IN CORSO...");

//             Utente admin = new Utente();
//             admin.setUsername(usernameAdmin);

//             String passwordInChiaro = "PassMakeUp2024!";
//             admin.setPassword(passwordEncoder.encode(passwordInChiaro));
//                                                                                              AGGIUNGERE EMAIL NEL CASO

//             admin.setRole("ADMIN");

//             utenteRepository.save(admin);

//             System.out.println(">>>> UTENTE ADMIN CREATO!");
//             System.out.println(">>>> USERNAME: " + usernameAdmin);
//             System.out.println(">>>> PASSWORD CRIPTATA SALVATA: " + admin.getPassword());
//         } else {
//             System.out.println(">>>> L'UTENTE ADMIN ESISTE GIA' NEL DATABASE.");
//         }
//     }
// }
