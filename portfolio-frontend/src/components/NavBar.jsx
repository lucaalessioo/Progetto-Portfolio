import { Link } from 'react-router-dom'; // Importa questo!

export default function Navbar() {
  return (
    <nav className="fixed top-0 w-full z-50 bg-white/80 backdrop-blur-md border-b border-gray-100 px-6 py-4 flex justify-between items-center">
      {/* ... i tuoi Login/Register ... */}

      <div className="hidden md:flex space-x-8 text-sm font-medium uppercase tracking-wider text-gray-600">
       
        <Link to="/" className="hover:text-pink-500 transition">Home</Link>
        <Link to="/portfolio" className="hover:text-pink-500 transition">Portfolio</Link>
        <a href="#servizi" className="hover:text-pink-500 transition">Servizi</a>
      </div>
    </nav>
  );
}