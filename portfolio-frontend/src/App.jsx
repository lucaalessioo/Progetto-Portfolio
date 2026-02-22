import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/NavBar";
import Hero from "./components/Hero";
import Portfolio from "./components/Portfolio";
import Login from "./components/Login";
import Servizi from "./components/Servizi";

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-white">
        <Navbar />
        <Routes>
          {/* Pagina Principale con il Video */}
          <Route path="/" element={<Hero />} />

          {/* Pagina dedicata al Portfolio */}
          <Route path="/portfolio" element={<Portfolio />} />
          <Route path="/login" element={<Login />} />
          <Route path="/servizi" element={<Servizi />} />
        </Routes>
      </div>
      <footer className="py-6 text-center border-t border-gray-100">
        <p className="text-[10px] text-gray-400 uppercase tracking-widest">
          © 2026 Federica Sofia Petrillo — 
          <a 
            href="https://www.iubenda.com/privacy-policy/IL_TUO_ID" 
            target="_blank" 
            rel="noreferrer"
            className="hover:text-[#5c2d2d] ml-2"
          >
            Privacy Policy
          </a>
        </p>
      </footer>
    </Router>
    
  );
}

export default App;
