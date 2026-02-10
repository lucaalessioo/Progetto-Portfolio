import React, {useEffect, useRef} from "react";
import { Link } from "react-router-dom";

export default function Hero() {

  const videoRef = useRef(null);

  useEffect(() => {
    if (videoRef.current) {
      videoRef.current.play().catch(error => {
        console.log("Autoplay bloccato dal browser:", error);
      });
    }
  }, []);

  return (
    <section className="relative h-screen w-full flex items-center justify-center overflow-hidden bg-[#5c2d2d]">
      {/* VIDEO DI SFONDO */}
      <video
        ref={videoRef}    // prova
        autoPlay
        loop
        muted
        playsInline
        className="absolute z-10 w-full h-full object-cover lg:object-contain lg:scale-110"
      >
        <source src="videosfondohome.mp4" type="video/mp4" />
        Il tuo browser non supporta i video.
      </video>

      {/* OVERLAY SCURO - Fondamentale per la leggibilità */}
      <div className="absolute inset-0 bg-black/40 z-20"></div>

      {/* CONTENUTO TESTUALE */}
      <div className="relative z-30 text-center px-4 animate-fadeIn">
        {/* NOME IN CORSIVO - Colore Crema */}
        <p className="text-2xl md:text-3xl font-serif italic text-[#f4f1ea] mb-2 drop-shadow-lg">
          Federica Sofia Petrillo
        </p>

        {/* SOTTOTITOLO - Tracking ampio */}
        <p className="text-[10px] md:text-xs uppercase tracking-[0.6em] text-white/70 font-light mb-8">
          Pro Make-up Artist
        </p>

        {/* TITOLO PRINCIPALE - Elegante */}
        <h1 className="text-5xl md:text-8xl font-serif italic text-white mb-8 drop-shadow-2xl">
          L'arte del Makeup
        </h1>

        <p className="text-gray-200 text-base md:text-lg max-w-lg mx-auto mb-10 font-light tracking-wide leading-relaxed">
          Valorizzo la tua bellezza naturale con tocchi professionali per ogni
          occasione speciale.
        </p>

        {/* CONTENITORE BOTTONI - Distanziati con gap */}
        <div className="flex flex-col md:flex-row gap-4 justify-center items-center">
          <Link
            to="/servizi"
            className="bg-[#5c2d2d] text-white px-10 py-4 rounded-full hover:bg-[#a64332] transition-all shadow-xl uppercase text-[10px] tracking-[0.2em] font-bold w-64 md:w-auto"
          >
            Scopri i Servizi
          </Link>

          <Link
            to="/portfolio"
            className="bg-white/10 backdrop-blur-md border border-white/30 text-white px-10 py-4 rounded-full hover:bg-white hover:text-gray-900 transition-all shadow-xl uppercase text-[10px] tracking-[0.2em] font-bold w-64 md:w-auto"
          >
            Vai al Portfolio
          </Link>
        </div>
      </div>
    </section>
  );
}
