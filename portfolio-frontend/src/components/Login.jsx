import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
      });

      if (response.ok) {
        const data = await response.json();
        localStorage.setItem('token', data.token); // Salviamo il "permesso"
        navigate('/portfolio'); // Torna al portfolio dopo il login
      } else {
        alert('Credenziali errate');
      }
    } catch (error) {
      console.error('Errore durante il login:', error);
    }
  };

  return (
    <div className="pt-40 min-h-screen bg-[#f4f1ea] flex justify-center">
      <form onSubmit={handleLogin} className="bg-white p-10 rounded-2xl shadow-xl h-fit w-96">
        <h2 className="text-3xl font-serif italic mb-6 text-[#8b3121]">Accedi</h2>
        <input 
          type="text" placeholder="Username" 
          className="w-full mb-4 p-3 border rounded"
          onChange={(e) => setUsername(e.target.value)}
        />
        <input 
          type="password" placeholder="Password" 
          className="w-full mb-6 p-3 border rounded"
          onChange={(e) => setPassword(e.target.value)}
        />
        <button className="w-full bg-[#8b3121] text-white py-3 rounded-full hover:bg-[#a64332] transition">
          Entra
        </button>
      </form>
    </div>
  );
}