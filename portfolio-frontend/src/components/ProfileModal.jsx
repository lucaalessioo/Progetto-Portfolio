import React, { useState, useEffect } from "react";

export default function ProfileModal({ isOpen, onClose }) {
  // 1. Inizializziamo gli stati vuoti
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  // 2. Usiamo useEffect correttamente al livello principale
  // Questo sincronizza i campi ogni volta che la modale viene aperta
  useEffect(() => {
    if (isOpen) {
      setUsername(localStorage.getItem("username") || "");
      setEmail(localStorage.getItem("email") || "");
      setPassword(""); // Resettiamo sempre la password per sicurezza
    }
  }, [isOpen]); 

  if (!isOpen) return null;

  const handleUpdate = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");

    try {
      const response = await fetch(
        "http://localhost:8080/api/auth/update-profile",
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({ username, email, password }),
        }
      );

      if (response.ok) {
        alert("Profilo aggiornato! Effettua di nuovo il login.");
        localStorage.clear(); 
        window.location.reload(); 
      } else {
        alert("Errore durante l'aggiornamento");
      }
    } catch (error) {
      console.error("Errore:", error);
    }
  };

  return (
    <div className="fixed inset-0 z-[100] flex items-center justify-center bg-black/60 backdrop-blur-sm p-4">
      <div className="bg-[#f4f1ea] w-full max-w-md rounded-3xl p-8 shadow-2xl animate-fadeIn">
        <h2 className="text-2xl font-serif italic text-[#5c2d2d] mb-6 text-center">
          Modifica Profilo
        </h2>

        <form onSubmit={handleUpdate} className="space-y-4">
          <div>
            <label className="text-[10px] uppercase tracking-widest text-gray-500 ml-2">
              Username
            </label>
            <input
              className="w-full p-3 bg-white border border-gray-200 rounded-xl focus:outline-[#5c2d2d]"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div>
            <label className="text-[10px] uppercase tracking-widest text-gray-500 ml-2">
              Email
            </label>
            <input
              className="w-full p-3 bg-white border border-gray-200 rounded-xl focus:outline-[#5c2d2d]"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div>
            <label className="text-[10px] uppercase tracking-widest text-gray-500 ml-2">
              Nuova Password (lascia vuota per non cambiare)
            </label>
            <input
              type="password"
              className="w-full p-3 bg-white border border-gray-200 rounded-xl focus:outline-[#5c2d2d]"
              placeholder="••••••••"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          <div className="flex gap-3 pt-4">
            <button
              type="submit"
              className="flex-1 bg-[#5c2d2d] text-white py-3 rounded-full font-bold uppercase text-[10px] tracking-widest hover:bg-[#a64332] transition-colors"
            >
              Salva Modifiche
            </button>
            <button
              type="button"
              onClick={onClose}
              className="px-6 py-3 text-gray-400 font-bold text-[10px] uppercase tracking-widest hover:text-gray-600"
            >
              Annulla
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}