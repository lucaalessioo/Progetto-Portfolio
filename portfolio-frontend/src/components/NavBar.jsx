export default function Navbar() {
  return (
    <nav className="fixed top-0 w-full z-50 bg-white/80 backdrop-blur-md border-b border-gray-100 px-6 py-4 flex justify-between items-center">
      <h1 className="flex gap-8 text-xs md:text-sm font-medium tracking-[0.2em] uppercase">
  <span className="font-bold text-pink-500 cursor-pointer hover:text-pink-600 transition">
    Login
  </span>
  <span className="font-bold text-pink-500 cursor-pointer hover:text-pink-600 transition">
    Register
  </span>
</h1>
      <div className="hidden md:flex space-x-8 text-sm font-medium uppercase tracking-wider text-gray-600">
        <a href="#portfolio" className="hover:text-pink-500 transition">Portfolio</a>
        <a href="#servizi" className="hover:text-pink-500 transition">Servizi</a>
      </div>
    </nav>
  );
}