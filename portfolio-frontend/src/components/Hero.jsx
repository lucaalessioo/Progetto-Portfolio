export default function Hero() {
  return (
    <section className="relative h-screen w-full flex items-center justify-center overflow-hidden">
      <video
        autoPlay
        loop
        muted
        playsInline
        className="absolute z-10 w-auto min-w-full min-h-full max-w-none object-cover"
      >
        <source src="4912015-hd_1920_1080_30fps.mp4" type="video/mp4" />
        Il tuo browser non supporta i video.
      </video>

      {/* OVERLAY SCURO (Serve per far leggere bene le scritte sopra il video) */}
        <div className="relative z-30 text-center px-4">
        
        {/* NOME IN CORSIVO */}
        <p className="text-3xl md:text-4xl font-serif italic text-red-600 mb-0 drop-shadow-lg">
          Federica Sofia Petrillo
        </p>
        
        {/* SOTTOTITOLO PICCOLO */}
        <p className="text-xs md:text-sm uppercase tracking-[0.5em] text-pink-300 font-bold mb-8 drop-shadow-md">
          Make up Artist
        </p>
        
        <h2 className="text-5xl md:text-8xl font-serif italic text-red-600 mb-6 drop-shadow-2xl">
          L'arte del Makeup 
        </h2>

        <p className="text-gray-100 text-lg max-w-lg mx-auto mb-8 font-light tracking-wide drop-shadow-md">
          Valorizzo la tua bellezza naturale per ogni occasione speciale.
        </p>

        <button className="bg-white text-gray-900 px-10 py-4 rounded-full hover:bg-pink-500 hover:text-white transition-all shadow-xl uppercase text-xs tracking-[0.2em] font-bold">
          Scopri i Servizi
        </button>
        <button className="bg-white text-gray-900 px-10 py-4 rounded-full hover:bg-pink-500 hover:text-white transition-all shadow-xl uppercase text-xs tracking-[0.2em] font-bold">
          Vai al Portfolio
        </button>
      </div>
    </section>
  );
  return <h1 style={{fontSize: '100px', color: 'red'}}>IO SONO LA HOME</h1>;
}