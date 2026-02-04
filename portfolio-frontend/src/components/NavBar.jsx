import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";

export default function Navbar() {
  const [isOpen, setIsOpen] = useState(false);
  const navigate = useNavigate();

  // Verifichiamo se l'utente è loggato controllando il token
  const isLoggedIn = localStorage.getItem("token") !== null;

  const closeMenu = () => setIsOpen(false);

  // Funzione per il Logout
  const handleLogout = () => {
    localStorage.removeItem("token"); // Rimuoviamo il permesso
    localStorage.removeItem("role");
    closeMenu();
    navigate("/"); // Riportiamo l'utente alla Home
    window.location.reload(); // rinfresca per aggiornare lo stato di tutti i componenti
  };

  return (
    <>
      {isOpen && (
        <div
          className="fixed inset-0 z-40 bg-black/5 md:hidden"
          onClick={closeMenu}
        ></div>
      )}

      <nav className="fixed top-0 w-full z-50 bg-[#f5f0e6]/90 backdrop-blur-md border-b border-[#5c2d2d]/10 px-6 py-4 flex justify-between items-center">
        {/* LOGO */}
        <Link
          to="/"
          onClick={closeMenu}
          className="text-lg font-serif italic text-[#5c2d2d] font-bold"
        >
          Portfolio
        </Link>

        {/* MENU DESKTOP */}
        <div className="hidden md:flex space-x-8 text-sm font-medium uppercase tracking-wider text-gray-600">
          <Link to="/" className="hover:text-[#5c2d2d] transition">
            Home
          </Link>
          <Link to="/portfolio" className="hover:text-[#5c2d2d] transition">
            Portfolio
          </Link>
          <Link to="/servizi" className="hover:text-[#5c2d2d] transition">
            Servizi
          </Link>
        </div>

        {/* BLOCCO DESTRA (Login/Register oppure Logout) */}
        <div className="flex items-center space-x-4">
          <div className="flex items-center space-x-3 text-xs font-bold uppercase tracking-widest">
            {!isLoggedIn ? (
              // Se NON è loggato, mostra questi:
              <>
                {/* Se NON è loggato, mostra questi: */}
                <Link
                  to="/login"
                  state={{ mode: "login" }} // Passiamo lo stato "login"
                  onClick={closeMenu}
                  className="text-gray-600 hover:text-[#5c2d2d] transition cursor-pointer"
                >
                  Login
                </Link>

                <Link
                  to="/login" // Entrambi vanno alla stessa rotta
                  state={{ mode: "register" }} // Ma questo passa "register"
                  onClick={closeMenu}
                  className="bg-[#5c2d2d] text-white px-4 py-2 rounded-full shadow-md hover:bg-[#a64332] transition cursor-pointer"
                >
                  Register
                </Link>
              </>
            ) : (
              // Se È LOGGATO, mostra solo il Logout:
              <button
                onClick={handleLogout}
                className="border border-[#5c2d2d] text-[#5c2d2d] px-5 py-2 rounded-full text-xs font-bold uppercase tracking-widest hover:bg-[#5c2d2d] hover:text-white transition-all duration-300 cursor-pointer shadow-sm"
              >
                Esci
              </button>
            )}
          </div>

          {/* TASTO HAMBURGER (Solo Mobile) */}
          <button
            onClick={() => setIsOpen(!isOpen)}
            className="md:hidden flex flex-col justify-center items-center w-8 h-8 space-y-1.5 z-50"
          >
            <span
              className={`block w-6 h-0.5 bg-[#5c2d2d] transition-all duration-300 ${isOpen ? "rotate-45 translate-y-2" : ""}`}
            ></span>
            <span
              className={`block w-6 h-0.5 bg-[#5c2d2d] transition-all duration-300 ${isOpen ? "opacity-0" : ""}`}
            ></span>
            <span
              className={`block w-6 h-0.5 bg-[#5c2d2d] transition-all duration-300 ${isOpen ? "-rotate-45 -translate-y-2" : ""}`}
            ></span>
          </button>
        </div>

        {/* MENU MOBILE */}
        <div
          className={`absolute top-full right-0 w-56 bg-[#f5f0e6] border-l border-b border-[#5c2d2d]/10 flex flex-col items-end space-y-6 py-10 px-10 text-sm font-medium uppercase tracking-widest text-[#5c2d2d]/80 transition-all duration-300 md:hidden shadow-2xl rounded-bl-3xl z-50 ${isOpen ? "opacity-100 visible translate-y-0" : "opacity-0 invisible -translate-y-5"}`}
        >
          <Link to="/" onClick={closeMenu} className="hover:text-[#5c2d2d]">
            Home
          </Link>
          <Link
            to="/portfolio"
            onClick={closeMenu}
            className="hover:text-[#5c2d2d]"
          >
            Portfolio
          </Link>
          <Link
            to="/servizi"
            onClick={closeMenu}
            className="hover:text-[#5c2d2d]"
          >
            Servizi
          </Link>

          {/* Aggiungiamo il Logout anche nel menu mobile se loggato */}
          {isLoggedIn && (
            <div className="w-full border-t border-[#5c2d2d]/10 mt-4 pt-4 flex justify-end">
              <button
                onClick={handleLogout}
                className="text-[#5c2d2d] font-bold uppercase tracking-widest text-xs cursor-pointer hover:opacity-70 transition-opacity"
              >
                Esci
              </button>
            </div>
          )}
        </div>
      </nav>
    </>
  );
}
