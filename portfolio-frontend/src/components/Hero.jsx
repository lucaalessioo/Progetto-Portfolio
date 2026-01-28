export default function Hero() {
  return (
    <section className="relative h-[90vh] flex items-center justify-center bg-gradient-to-b from-pink-50 to-white px-4">
      <div className="text-center">
        <h2 className="text-5xl md:text-7xl font-serif italic text-gray-900 mb-6">
          L'arte del Makeup
        </h2>
        <p className="text-gray-600 text-lg max-w-lg mx-auto mb-8 font-light tracking-wide">
          Valorizzo la tua bellezza naturale per ogni occasione speciale.
        </p>
        <button className="bg-pink-500 text-white px-10 py-4 rounded-full hover:bg-pink-600 transition shadow-xl uppercase text-xs tracking-[0.2em]">
          Scopri i Servizi
        </button>
      </div>
    </section>
  );
}