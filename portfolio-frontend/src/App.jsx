import Navbar from './components/Navbar';
import Hero from './components/Hero'; // Aggiungi questo!

function App() {
  return (
    <div className="min-h-screen bg-white">
      <Navbar />
      <main> 
        <Hero />
        {/* Qui sotto arriverà il Portfolio preso dal database! */}
      </main>
    </div>
  );
}

export default App;