import { Link } from 'react-router-dom'; 

export default function Navbar() {
  return (
    <nav className="fixed top-0 w-full z-50 bg-white/80 backdrop-blur-md border-b border-gray-100 px-6 py-4 flex justify-between items-center">
      {/* ... i tuoi Login/Register ... */}

      <div className="hidden md:flex space-x-8 text-sm font-medium uppercase tracking-wider text-gray-600">
       
        <Link to="/" className="hover:text-pink-500 transition">Home</Link>
        <Link to="/portfolio" className="hover:text-pink-500 transition">Portfolio</Link>
        <Link to="/servizi" className="hover:text-pink-500 transition">Servizi</Link>
        
      </div>
      {/* LOGIN / REGISTER (Destra) */}
      <div className="flex items-center space-x-5 text-xs font-bold uppercase tracking-widest">
        <Link 
          to="/login" 
          className="text-gray-600 hover:text-[#8b3121] transition border-b border-transparent hover:border-[#8b3121]"
        >
          Login
        </Link>
        <Link 
          to="/register" 
          className="bg-[#8b3121] text-white px-4 py-2 rounded-full hover:bg-[#a64332] transition shadow-md"
        >
          Register
        </Link>
      </div>
    </nav>
  );
}