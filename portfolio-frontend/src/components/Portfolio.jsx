import React, { useState, useEffect } from 'react';

export default function Portfolio() {
  const [photos, setPhotos] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/works')
      .then(response => response.json())
      .then(data => {
        setPhotos(data); 
      })
      .catch(error => console.error('Errore nel caricamento', error));
  }, []);

  return (
    <section id="portfolio" className="pt-32 pb-20 px-6 bg-white">
      <div className="max-w-7xl mx-auto text-center mb-16">
        <h2 className="text-4xl font-serif italic mb-4">I Miei Lavori</h2>
        <div className="h-1 w-20 bg-pink-500 mx-auto"></div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">

        {photos.map((photo) => (

          <div key={photo.id} className="group relative overflow-hidden rounded-lg shadow-lg">
            <img 
              src={`http://localhost:8080${photo.imageUrl}`} 
              alt={photo.title} 
              className="w-full h-80 object-cover transition-transform duration-500 group-hover:scale-110"
            />
            <div className="absolute inset-0 bg-black/40 opacity-0 group-hover:opacity-100 transition-opacity flex items-center justify-center">
              <p className="text-white font-medium uppercase tracking-widest">{photo.title}</p>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
}