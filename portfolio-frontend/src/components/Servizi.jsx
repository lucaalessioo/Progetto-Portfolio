import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import ServiceCard from "./ServiceCard";

export default function Servizi() {
  //Stati per la modale di prenotazione
  const [isBookingModalOpen, setIsBookingModalOpen] = useState(false);
  const [selectedService, setSelectedService] = useState(null);
  const [bookingDate, setBookingDate] = useState("");
  const navigate = useNavigate();

  // apre la modale di quel tipo di servizio scelto
  const openBookingModal = (servizio) => {
    setSelectedService(servizio);
    setIsBookingModalOpen(true);
  };

  // const handleConfirmBooking = async () => {
  //   if (!bookingDate) {
  //     alert("Per favore, seleziona una data.");
  //     return;
  //   }

  //   const token = localStorage.getItem("token");
  //   const username = localStorage.getItem("username");
  //   const emailUtente = localStorage.getItem("email"); // Salvata durante il login

  //   const bookingRequest = {
  //     customerName: username,
  //     customerEmail: emailUtente,
  //     serviceTitle: selectedService.title,
  //     date: bookingDate,
  //   };

  //   try {
  //     const response = await fetch("http://localhost:8080/api/bookings", {
  //       method: "POST",
  //       headers: {
  //         "Content-Type": "application/json",
  //         Authorization: `Bearer ${token}`, // L'utente deve essere loggato
  //       },
  //       body: JSON.stringify(bookingRequest),
  //     });

  //     if (response.ok) {
  //       alert("Richiesta inviata!");
  //       setIsBookingModalOpen(false);
  //       setBookingDate("");
  //     } else {
  //       alert("Errore durante l'invio. Assicurati di essere loggato.");
  //     }
  //   } catch (error) {
  //     console.error("Errore tecnico:", error);
  //   }
  // };
  const handleConfirmBooking = async () => {
  const token = localStorage.getItem("token");

  // Non inviamo più l'email manualmente, il server la prenderà dal Token!
  const bookingRequest = {
    serviceTitle: selectedService.title,
    date: bookingDate,
  };

  try {
    const response = await fetch("http://localhost:8080/api/bookings", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(bookingRequest),
    });

    if (response.ok) {
      alert("Richiesta inviata! Federica ti risponderà presto.");
      setIsBookingModalOpen(false);
    }
  } catch (error) {
    alert("Errore nell'invio della prenotazione.");
  }
};

  const serviziData = [
    {
      title: "Trucco Sposa",
      price: "Da definire",
      image:
        "http://localhost:8080/uploads/foto-copertina-card-sposa.jpeg",
      features: [
        "Prova trucco inclusa",
        "Studio della pelle e consulenza",
        "Applicazione ciglia finte",
        "Kit ritocco per il giorno del sì",
      ],
    },
    {
      title: "Trucco Evento",
      price: "Da definire",
      image:
        "http://localhost:8080/uploads/foto-copertina-card-evento.jpeg",
      features: [
        "Trucco personalizzato (Giorno o Sera)",
        "Contouring professionale",
        "Fissaggio lunga durata",
        "Consulenza look",
      ],
    },
    {
      title: "Trucco per i tuoi 18",
      price: "Da definire",
      image:
        "http://localhost:8080/uploads/foto-copertina-card-18.jpeg",
      features: [
        "Analisi della tua trousse",
        "Tecniche base e avanzate",
        "Scelta dei prodotti ideali",
        "Dispensa riassuntiva",
      ],
    },
  ];

  return (
    <section
      id="servizi"
      className="pt-32 pb-20 px-4 md:px-10 bg-[#d6c7b8] min-h-screen"
    >
      <div className="max-w-7xl mx-auto text-center mb-16">
        <h2 className="text-5xl md:text-6xl font-serif italic mb-4 text-[#5c2d2d]">
          I Miei Servizi
        </h2>
        {/* <div className="w-24 h-1 bg-[#a64332] mx-auto mb-6"></div> */}
        <p className="text-gray-700 max-w-2xl mx-auto italic">
          "La bellezza risiede nei dettagli. Ogni servizio è pensato per
          esaltare la tua unicità attraverso tecniche professionali e prodotti
          di alta qualità."
        </p>
      </div>

      <div className="max-w-7xl mx-auto grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
        {serviziData.map((servizio, index) => (

       <div 
      key={index} 
      className="animate-fade-in" 
      style={{ 
        animationDelay: `${index * 400}ms`, 
        animationFillMode: 'backwards' // Mantiene la card invisibile finché non tocca a lei
      }}
    >

          <ServiceCard
            key={index}
            title={servizio.title}
            price={servizio.price}
            image={servizio.image}
            features={servizio.features}
            onBook={() => {
              const token = localStorage.getItem("token");
              if (!token) {
                alert("Devi essere loggato per prenotare!");
                navigate("/login");
                return;
              }
              openBookingModal(servizio);
            }}
          />
          </div>
        ))}
      </div>

      {isBookingModalOpen && (
        <div className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl p-8 max-w-md w-full shadow-2xl">
            <h3 className="text-2xl font-serif text-[#5c2d2d] mb-4">
              Prenota: {selectedService?.title}
            </h3>

            <p className="text-sm text-gray-600 mb-6">
              Seleziona la data in cui vorresti il servizio. Riceverai una
              conferma via email.
            </p>

            <div className="flex flex-col gap-4">
              <label className="text-xs font-bold uppercase tracking-widest text-[#a64332]">
                Scegli la Data
              </label>
              <input
                type="date"
                // Impedisce di selezionare date passate
                min={new Date().toISOString().split("T")[0]}
                value={bookingDate}
                onChange={(e) => setBookingDate(e.target.value)}
                className="p-3 border rounded-lg focus:outline-[#5c2d2d]"
                required
              />

              <div className="flex gap-3 mt-4">
                <button
                  onClick={handleConfirmBooking}
                  className="flex-1 bg-green-600 text-white py-3 rounded-lg font-bold uppercase text-xs tracking-widest hover:bg-green-700 transition-colors"
                >
                  Conferma e Invia
                </button>
                <button
                  onClick={() => setIsBookingModalOpen(false)}
                  className="px-6 py-3 text-gray-500 font-bold hover:bg-gray-100 rounded-lg"
                >
                  ANNULLA
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </section>
  );
}
