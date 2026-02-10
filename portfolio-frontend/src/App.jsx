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
    </Router>
  );
}

export default App;
