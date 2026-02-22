import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import ProfileModal from "./ProfileModal";

export default function Navbar() {
  const [isOpen, setIsOpen] = useState(false);
  const [isProfileModalOpen, setIsProfileModalOpen] = useState(false);
  const navigate = useNavigate();

  const isLoggedIn = localStorage.getItem("token") !== null;
  const username = localStorage.getItem("username");

  const closeMenu = () => setIsOpen(false);

  const handleLogout = () => {
    localStorage.clear();
    closeMenu();
    navigate("/");
    window.location.reload();
  };

  return (
    <>
      {isOpen && (
        <div className="fixed inset-0 z-40 bg-black/5 md:hidden" onClick={closeMenu}></div>
      )}

      <nav className="fixed top-0 w-full z-50 bg-[#f5f0e6]/70 backdrop-blur-xl border-b border-[#5c2d2d]/10 px-6 py-4 transition-all duration-300">
        <div className="max-w-7xl mx-auto flex justify-between items-center relative">
          
          {/* 1. LOGO */}
          <Link to="/" onClick={closeMenu} className="text-lg font-serif italic text-[#5c2d2d] font-bold">
            Portfolio
          </Link>

          {/* 2. MENU DESKTOP */}
          <div className="hidden md:flex absolute left-1/2 -translate-x-1/2 space-x-8 text-sm font-medium uppercase tracking-wider text-gray-600">
            <Link to="/" className="hover:text-[#5c2d2d] transition">Home</Link>
            <Link to="/portfolio" className="hover:text-[#5c2d2d] transition">Portfolio</Link>
            <Link to="/servizi" className="hover:text-[#5c2d2d] transition">Servizi</Link>
          </div>

          {/* 3. BLOCCO DESTRA */}
          <div className="flex items-center space-x-4">
            <div className="flex items-center space-x-6 text-xs font-bold uppercase tracking-widest">
              {!isLoggedIn ? (
                <>
                  <Link to="/login" state={{ mode: "login" }} className="text-gray-600 hover:text-[#5c2d2d] transition cursor-pointer">
                    Login
                  </Link>
                  <Link to="/login" state={{ mode: "register" }} className="bg-[#5c2d2d] text-white px-4 py-2 rounded-full shadow-md hover:bg-[#a64332] transition cursor-pointer">
                    Register
                  </Link>
                </>
              ) : (
                <div className="flex items-center gap-4">
                  <div className="hidden min-[1100px]:flex flex-col items-end leading-tight border-r border-[#5c2d2d]/10 pr-4">
                    <span className="text-[9px] uppercase tracking-[0.2em] text-gray-400 font-bold">Account attivo</span>
                    <span className="text-sm font-serif italic text-[#5c2d2d]">{username}</span>
                  </div>

                  <div className="hidden md:flex items-center gap-6 text-[10px] font-bold uppercase tracking-widest">
                    <button onClick={() => setIsProfileModalOpen(true)} className="text-[#5c2d2d] hover:opacity-70 transition cursor-pointer">
                      Modifica Profilo
                    </button>
                    <button onClick={handleLogout} className="text-gray-600 hover:text-[#5c2d2d] transition cursor-pointer">
                      Esci
                    </button>
                  </div>
                </div>
              )}
            </div>

            <button onClick={() => setIsOpen(!isOpen)} className="md:hidden flex flex-col justify-center items-center w-8 h-8 space-y-1.5 z-50 ml-2">
              <span className={`block w-6 h-0.5 bg-[#5c2d2d] transition-all duration-300 ${isOpen ? "rotate-45 translate-y-2" : ""}`}></span>
              <span className={`block w-6 h-0.5 bg-[#5c2d2d] transition-all duration-300 ${isOpen ? "opacity-0" : ""}`}></span>
              <span className={`block w-6 h-0.5 bg-[#5c2d2d] transition-all duration-300 ${isOpen ? "-rotate-45 -translate-y-2" : ""}`}></span>
            </button>
          </div>
        </div>

        {/* MENU MOBILE */}
        <div className={`absolute top-full right-0 w-64 bg-[#f5f0e6] border-l border-b border-[#5c2d2d]/10 flex flex-col items-end space-y-6 py-10 px-10 text-sm font-medium uppercase tracking-widest text-[#5c2d2d]/80 transition-all duration-300 md:hidden shadow-2xl rounded-bl-3xl z-50 ${isOpen ? "opacity-100 visible translate-y-0" : "opacity-0 invisible -translate-y-5"}`}>
          {isLoggedIn && (
            <div className="text-right mb-4 border-b border-[#5c2d2d]/10 pb-4 w-full">
              <p className="text-[10px] text-gray-400 uppercase tracking-widest mb-1">Benvenuta,</p>
              <p className="text-lg font-serif italic text-[#5c2d2d]">{username}</p>
            </div>
          )}
          <Link to="/" onClick={closeMenu}>Home</Link>
          <Link to="/portfolio" onClick={closeMenu}>Portfolio</Link>
          <Link to="/servizi" onClick={closeMenu}>Servizi</Link>
          {isLoggedIn && (
            <div className="w-full border-t border-[#5c2d2d]/10 mt-4 pt-4 flex flex-col items-end gap-6 text-xs font-bold">
              <button onClick={() => {setIsProfileModalOpen(true); closeMenu();}} className="text-[#5c2d2d]">Modifica Profilo</button>
              <button onClick={handleLogout} className="text-gray-600">Esci</button>
            </div>
          )}
        </div>
      </nav>

      {isProfileModalOpen && (
        <ProfileModal isOpen={isProfileModalOpen} onClose={() => setIsProfileModalOpen(false)} />
      )}
    </>
  );
}