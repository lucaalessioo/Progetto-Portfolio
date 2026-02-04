import React from 'react';

const ServiceCard = ({ title, price, features, image }) => {
  return (
    <div className="bg-[#f4f1ea] rounded-2xl overflow-hidden shadow-lg transition-transform duration-300 hover:-translate-y-2 flex flex-col h-full border border-[#d6c7b8]">
      {/* Immagine di testata del servizio */}
      <div className="h-48 overflow-hidden">
        <img 
          src={image} 
          alt={title} 
          className="w-full h-full object-cover transition-transform duration-500 hover:scale-110"
        />
      </div>

      {/* Contenuto della Card */}
      <div className="p-8 flex flex-col flex-grow">
        <h3 className="text-2xl font-serif italic text-[#5c2d2d] mb-2">{title}</h3>
        <p className="text-[#a64332] font-bold text-lg mb-6">{price}</p>
        
        {/* Elenco caratteristiche */}
        <ul className="space-y-3 mb-8 flex-grow">
          {features.map((item, index) => (
            <li key={index} className="flex items-start gap-3 text-sm text-gray-700">
              <span className="text-[#a64332]">✦</span>
              {item}
            </li>
          ))}
        </ul>

        <button className="w-full py-3 border border-[#5c2d2d] text-[#5c2d2d] uppercase text-xs tracking-widest font-bold rounded-lg hover:bg-[#5c2d2d] hover:text-white transition-all duration-300">
          Prenota Consulenza
        </button>
      </div>
    </div>
  );
};

export default ServiceCard;