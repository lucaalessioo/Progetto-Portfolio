import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";

export default function Login() {
  const location = useLocation();
  const navigate = useNavigate();

  const [isLogin, setIsLogin] = useState(true);
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState(""); // Stato dell'email che avevi aggiunto
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  useEffect(() => {
    if (location.state?.mode === "register") {
      setIsLogin(false);
    } else {
      setIsLogin(true);
    }
  }, [location]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!isLogin && password !== confirmPassword) {
      alert("Le password non coincidono");
      return;
    }

    const endpoint = isLogin ? "login" : "register";

    // 1. Prepariamo l'oggetto da inviare. Se è registrazione, aggiungiamo l'email.
    const bodyPayload = isLogin
      ? { username, password }
      : { username, password, email }; // Qui includiamo l'email

    try {
      const response = await fetch(
        `http://localhost:8080/api/auth/${endpoint}`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(bodyPayload),
        },
      );

      if (response.ok) {
        const data = await response.json(); // Leggiamo la risposta JSON in ogni caso

        if (isLogin) {
          // 2. Salvataggio dati nel localStorage
          localStorage.setItem("token", data.token);
          localStorage.setItem("role", data.role);
          localStorage.setItem("email", data.email); // Salviamo l'email ricevuta dal backend
          localStorage.setItem("username", username); // Utile per la prenotazione

          navigate("/");
        } else {
          alert("Registrazione completata! Ora puoi accedere.");
          setIsLogin(true);
        }
      } else {
        alert(
          isLogin ? "Credenziali errate" : "Errore durante la registrazione",
        );
      }
    } catch (error) {
      console.error("Errore:", error);
    }
  };

  return (
    <div className="pt-40 min-h-screen bg-[#d6c7b8] flex justify-center px-4">
      <form
        onSubmit={handleSubmit}
        className="bg-[#f5f0e6] p-10 rounded-2xl shadow-xl h-fit w-96 border border-[#5c2d2d]/10"
      >
        <h2 className="text-3xl font-serif italic mb-6 text-[#5c2d2d]">
          {isLogin ? "Accedi" : "Registrati"}
        </h2>

        <input
          type="text"
          placeholder="Username"
          className="w-full mb-4 p-3 border rounded focus:outline-[#5c2d2d]"
          onChange={(e) => setUsername(e.target.value)}
          required
        />

        {/* 3. MOSTRO IL CAMPO EMAIL SOLO IN REGISTRAZIONE */}
        {!isLogin && (
          <input
            type="email"
            placeholder="Email"
            value={email}
            className="w-full mb-4 p-3 border rounded focus:outline-[#5c2d2d] animate-fadeIn"
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        )}

        <input
          type="password"
          placeholder="Password"
          className="w-full mb-4 p-3 border rounded focus:outline-[#5c2d2d]"
          onChange={(e) => setPassword(e.target.value)}
          required
        />

        {!isLogin && (
          <input
            type="password"
            placeholder="Conferma Password"
            className="w-full mb-6 p-3 border rounded focus:outline-[#5c2d2d] animate-fadeIn"
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
          />
        )}

        <button className="w-full bg-[#5c2d2d] text-white py-3 rounded-full hover:bg-[#a64332] transition font-bold uppercase tracking-widest text-xs">
          {isLogin ? "Entra" : "Crea Account"}
        </button>

        <div className="mt-6 text-center">
          <button
            type="button"
            onClick={() => setIsLogin(!isLogin)}
            className="text-xs text-gray-600 hover:text-[#5c2d2d] underline tracking-wider cursor-pointer transition-colors duration-200"
          >
            {isLogin
              ? "Non hai un account? Registrati"
              : "Hai già un account? Accedi"}
          </button>
        </div>
      </form>
    </div>
  );
}
