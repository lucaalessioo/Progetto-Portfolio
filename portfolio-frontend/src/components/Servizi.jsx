import React from 'react';
import ServiceCard from './ServiceCard';

export default function Servizi() {
  const serviziData = [
    {
      title: "Trucco Sposa",
      price: "Da definire",
      image: "https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9?q=80&w=500",
      features: [
        "Prova trucco inclusa",
        "Studio della pelle e consulenza",
        "Applicazione ciglia finte",
        "Kit ritocco per il giorno del sì"
      ]
    },
    {
      title: "Trucco Evento",
      price: "Da definire",
      image: "https://images.unsplash.com/photo-1487412720507-e7ab37603c6f?q=80&w=500",
      features: [
        "Trucco personalizzato (Giorno o Sera)",
        "Contouring professionale",
        "Fissaggio lunga durata",
        "Consulenza look"
      ]
    },
    {
      title: "Lezioni Self-Makeup",
      price: "Da definire",
      image: "https://images.unsplash.com/photo-1512496015851-a90fb38ba796?q=80&w=500",
      features: [
        "Analisi della tua trousse",
        "Tecniche base e avanzate",
        "Scelta dei prodotti ideali",
        "Dispensa riassuntiva"
      ]
    }
  ];

  return (
    <section id="servizi" className="pt-32 pb-20 px-4 md:px-10 bg-[#d6c7b8] min-h-screen">
      <div className="max-w-7xl mx-auto text-center mb-16">
        <h2 className="text-5xl md:text-6xl font-serif italic mb-4 text-[#5c2d2d]">
          I Miei Servizi
        </h2>
        {/* <div className="w-24 h-1 bg-[#a64332] mx-auto mb-6"></div> */}
        <p className="text-gray-700 max-w-2xl mx-auto italic">
          "La bellezza risiede nei dettagli. Ogni servizio è pensato per esaltare la tua unicità attraverso tecniche professionali e prodotti di alta qualità."
        </p>
      </div>

      <div className="max-w-7xl mx-auto grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
        {serviziData.map((servizio, index) => (
          <ServiceCard 
            key={index}
            title={servizio.title}
            price={servizio.price}
            image={servizio.image}
            features={servizio.features}
          />
        ))}
      </div>
    </section>
  );
}