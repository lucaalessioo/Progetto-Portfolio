export default function Navbar() {
  return (
    <nav className="fixed top-0 w-full z-50 bg-white/80 backdrop-blur-md border-b border-gray-100 px-6 py-4 flex justify-between items-center">
      <h1 className="text-xl font-light tracking-widest uppercase text-gray-800">
        Nome <span className="font-bold text-pink-500">Artist</span>
      </h1>
      <div className="hidden md:flex space-x-8 text-sm font-medium uppercase tracking-wider text-gray-600">
        <a href="#portfolio" className="hover:text-pink-500 transition">Portfolio</a>
        <a href="#servizi" className="hover:text-pink-500 transition">Servizi</a>
      </div>
    </nav>
  );
}