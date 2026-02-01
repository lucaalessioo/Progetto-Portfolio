import React, { useState, useEffect } from 'react';

export default function Portfolio() {
  const [photos, setPhotos] = useState([]);
  const isAdmin = localStorage.getItem('token') !== null;

  useEffect(() => {
    fetch('http://localhost:8080/api/works')
      .then(response => response.json())
      .then(data => {
      setPhotos(data); 
      })
      .catch(error => console.error('Errore nel caricamento', error));
  }, []);

  return (
/* 1. SFONDO CHIARO CIRCOSTANTE: bg-[#f4f1ea] (preso dallo stile Canva) */
    <section id="portfolio" className="pt-32 pb-20 px-4 md:px-10 bg-[#f4f1ea] min-h-screen">
      
      {/* Intestazione fuori dal rettangolo scuro */}
      <div className="max-w-7xl mx-auto text-center mb-16">
        <h2 className="text-5xl md:text-6xl font-serif italic mb-4 text-[#8b3121]">
          I Miei Lavori
        </h2>
      </div>
      {isAdmin && (
  <div className="mb-10 text-center">
    <button className="bg-green-600 text-white px-6 py-2 rounded-full font-bold uppercase text-xs tracking-widest">
      + Aggiungi Nuovo Lavoro
    </button>
    <button 
      onClick={() => { localStorage.removeItem('token'); window.location.reload(); }}
      className="ml-4 bg-gray-500 text-white px-6 py-2 rounded-full font-bold uppercase text-xs tracking-widest"
    >
      Logout
    </button>
  </div>
)}

{/* CONTENITORE RETTANGOLARE SCURO AGGIORNATO */}
<div className="max-w-4xl mx-auto bg-[#8b3121] py-20 px-6 md:px-20 
                shadow-[0_20px_50px_rgba(0,0,0,0.3)] /* Ombra profonda */
                rounded-2xl border-t-8 border-[#7a2a1c]
                relative overflow-hidden">
  
  {/* Effetto decorativo: un sottile bordo interno per dare un tocco di classe */}
  <div className="absolute inset-4 border border-white/10 pointer-events-none rounded-xl"></div>

  <div className="flex flex-col items-center gap-24 relative z-10">
    {photos.map((photo) => (
      <div 
        key={photo.id} 
        className="group relative w-full max-w-md transition-all duration-500"
      >
        {/* Effetto cornice per la foto */}
        <div className="relative p-1 bg-[#a64332] rounded-lg shadow-2xl"> 
          <img 
            src={`http://localhost:8080${photo.imageUrl}`} 
            alt={photo.title} 
            className="w-full aspect-square object-cover rounded-md transition-transform duration-700 group-hover:scale-[1.02]"
          />
        </div>
        
        {/* Didascalia sotto la foto invece dell'overlay (molto più "Fashion Magazine") */}
        <div className="mt-4 text-center">
          <p className="text-[#f4f1ea] font-serif italic text-xl opacity-80 group-hover:opacity-100 transition-opacity uppercase tracking-widest">
            {photo.title}
          </p>
        </div>
      </div>
    ))}
  </div>
</div>
    </section>
  );
}