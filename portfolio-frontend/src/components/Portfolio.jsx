import React, { useState, useEffect } from "react";

export default function Portfolio() {
  const [photos, setPhotos] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("All");

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingWork, setEditingWork] = useState(null); // null per nuovo, oggetto per modifica

  // Stati del form
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [categoryId, setCategoryId] = useState("");
  const [file, setFile] = useState(null);

  const [openMenuId, setOpenMenuId] = useState(null);
  const userRole = localStorage.getItem("role");
  const isAdmin = userRole === "ADMIN" || userRole === "ROLE_ADMIN";

  // 1. Caricamento dati iniziali
  useEffect(() => {
    // Caricamento Lavori
    fetch("http://localhost:8080/api/works")
      .then((response) => {
        if (!response.ok) throw new Error("Errore lavori " + response.status);
        return response.json();
      })
      .then((data) => setPhotos(data))
      .catch((error) => console.error("Errore lavori:", error));

    fetch("http://localhost:8080/api/category")
      .then((response) => {
        if (!response.ok)
          throw new Error("Errore categorie " + response.status);
        return response.json();
      })
      .then((data) => setCategories(data))
      .catch((error) => console.error("Errore categorie:", error));
  }, []);

  // 2. Chiusura automatica menu tre puntini
  useEffect(() => {
    const closeMenu = () => setOpenMenuId(null);
    if (openMenuId !== null) window.addEventListener("click", closeMenu);
    return () => window.removeEventListener("click", closeMenu);
  }, [openMenuId]);

  // 3. Funzione per aprire il modale in modalità "Modifica"
  const openEditModal = (photo) => {
    setEditingWork(photo);
    setTitle(photo.title);
    setDescription(photo.description);
    setCategoryId(photo.categories?.id || "");
    setIsModalOpen(true); //Form per modificare una foto
    setOpenMenuId(null); //Menu a tendina resettato a null per rimuovere l Event nello useEffect
  };

  // 4. Reset del form quando si chiude il modale
  const closeAndResetModal = () => {
    setIsModalOpen(false);
    setEditingWork(null); // se dentro editing work non compare un oggetto il programma sa che deve aggiungerne uno altrimente modifica quello che gli arriva
    setTitle("");
    setDescription("");
    setCategoryId("");
    setFile(null);
  };

  // 5. Gestione Inserimento o Modifica (POST o PUT)
  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");
    const formData = new FormData();
    formData.append("title", title);
    formData.append("description", description);
    formData.append("categoryId", categoryId);
    if (file) formData.append("file", file); // se ci sta una foto la aggiunge altrimenti no

    const url = editingWork
      ? `http://localhost:8080/api/works/${editingWork.id}`
      : "http://localhost:8080/api/works";

    const method = editingWork ? "PUT" : "POST"; // se arriva un oggetto lo modifica altrimenti lo pubblica come nuovo

    try {
      const response = await fetch(url, {
        method: method,
        headers: { Authorization: `Bearer ${token}` },
        body: formData,
      });

      if (response.ok) {
        const result = await response.json();
        if (editingWork) {
          setPhotos(photos.map((p) => (p.id === result.id ? result : p))); // Modifica
        } else {
          setPhotos([result, ...photos]); // Aggiunta all inizio della griglia
        }
        closeAndResetModal();
        alert(editingWork ? "Modifica completata" : "Lavoro caricato");
      }
    } catch (error) {
      alert("Errore tecnico: " + error.message);
    }
  };

  // 6. Eliminazione
  const handleDelete = async (id) => {
    if (window.confirm("Vuoi davvero eliminare questa immagine?")) {
      const token = localStorage.getItem("token");
      try {
        const response = await fetch(`http://localhost:8080/api/works/${id}`, {
          method: "DELETE",
          headers: { Authorization: `Bearer ${token}` },
        });
        if (response.ok) {
          setPhotos(photos.filter((p) => p.id !== id));
          alert("Rimossa con successo");
        }
      } catch (error) {
        console.error(error);
      }
    }
  };

  // FILTRO CATEGORIE
  const filteredPhotos =
    selectedCategory === "All"
      ? photos
      : photos.filter((p) => p.categories?.name === selectedCategory);

  return (
    <section
      id="portfolio"
      className="pt-32 pb-20 px-4 md:px-10 bg-[#d6c7b8] min-h-screen"
    >
      <div className="max-w-7xl mx-auto text-center mb-16">
        <h2 className="text-5xl md:text-6xl font-serif italic mb-4 text-[#5c2d2d]">
          I Miei Lavori
        </h2>

        {/* BARRA CATEGORIE (Desktop e Mobile) */}
        <div className="mt-10 flex flex-col items-center">
          {/* Desktop */}
          <div className="hidden md:flex gap-8 border-b border-[#5c2d2d]/20 pb-4">
            {/* Bottone "Tutte" */}
            <button
              onClick={() => setSelectedCategory("All")}
              className={`uppercase tracking-widest text-sm transition-all duration-300 cursor-pointer pb-1 border-b-2 ${
                selectedCategory === "All"
                  ? "text-[#5c2d2d] font-bold border-[#5c2d2d]"
                  : "text-gray-400 border-transparent hover:text-[#5c2d2d] hover:border-[#5c2d2d]"
              }`}
            >
              Tutte
            </button>

            {/* Bottoni Dinamici */}
            {categories.map((cat) => (
              <button
                key={cat.id}
                onClick={() => setSelectedCategory(cat.name)}
                className={`uppercase tracking-widest text-sm transition-all duration-300 cursor-pointer pb-1 border-b-2 ${
                  selectedCategory === cat.name
                    ? "text-[#5c2d2d] font-bold border-[#5c2d2d]"
                    : "text-gray-400 border-transparent hover:text-[#5c2d2d] hover:border-[#5c2d2d]"
                }`}
              >
                {cat.name}
              </button>
            ))}
          </div>

          {/* Mobile Select */}
          <select
            onChange={(e) => setSelectedCategory(e.target.value)}
            className="md:hidden p-2 bg-[#5c2d2d] text-white rounded-lg uppercase text-xs tracking-tighter cursor-pointer"
          >
            <option value="All">Filtra per Categoria</option>
            {categories.map((cat) => (
              <option key={cat.id} value={cat.name}>
                {cat.name}
              </option>
            ))}
          </select>
        </div>
      </div>

      {isAdmin && (
        <div className="mb-10 text-center">
          <button
            onClick={() => setIsModalOpen(true)}
            className="border-2 border-[#5c2d2d] text-[#5c2d2d] px-8 py-3 rounded-full font-bold uppercase text-xs tracking-[0.2em] cursor-pointer hover:bg-[#5c2d2d] hover:text-white transition-all duration-300 shadow-sm hover:shadow-lg"
          >
            + Aggiungi Nuovo Lavoro
          </button>
        </div>
      )}

      {/* MODALE (Inserimento/Modifica) */}
      {isModalOpen && (
        <div className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl p-8 max-w-md w-full shadow-2xl">
            <h3 className="text-2xl font-serif text-[#8b3121] mb-6">
              {editingWork ? "Modifica Lavoro" : "Carica Nuovo Lavoro"}
            </h3>
            <form onSubmit={handleSubmit} className="flex flex-col gap-4">
              <input
                value={title}
                type="text"
                placeholder="Titolo"
                className="p-3 border rounded-lg focus:outline-[#8b3121]"
                onChange={(e) => setTitle(e.target.value)}
                required
              />
              <textarea
                value={description}
                placeholder="Descrizione"
                className="p-3 border rounded-lg focus:outline-[#8b3121]"
                onChange={(e) => setDescription(e.target.value)}
              />

              {/* SELECT CATEGORIA */}
              <select
                value={categoryId}
                onChange={(e) => setCategoryId(e.target.value)}
                className="p-3 border rounded-lg focus:outline-[#8b3121] bg-white"
                required
              >
                <option value="">Seleziona Categoria</option>
                {categories.map((cat) => (
                  <option key={cat.id} value={cat.id}>
                    {cat.name}
                  </option>
                ))}
              </select>

              <input
                type="file"
                className="text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded-full file:bg-[#8b3121] file:text-white"
                onChange={(e) => setFile(e.target.files[0])}
                required={!editingWork}
              />

              <div className="flex gap-3 mt-4">
                <button
                  type="submit"
                  className="flex-1 bg-green-600 text-white py-3 rounded-lg font-bold uppercase text-xs tracking-widest"
                >
                  {editingWork ? "Salva Modifiche" : "Pubblica"}
                </button>
                <button
                  type="button"
                  onClick={closeAndResetModal}
                  className="px-6 py-3 text-gray-500 font-bold hover:bg-gray-100 rounded-lg"
                >
                  ANNULLA
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* GRIGLIA FOTO */}
      <div className="max-w-4xl mx-auto bg-[#5c2d2d] py-20 px-6 md:px-20 shadow-2xl rounded-2xl relative overflow-hidden">
        <div className="absolute inset-4 border border-white/10 pointer-events-none rounded-xl"></div>
        <div className="flex flex-col items-center gap-24 relative z-10">
          {filteredPhotos.map((photo) => (
            <div
              key={photo.id}
              className="group relative w-full max-w-md animate-fadeIn transition-all duration-500 ease-in-out"
            >
              {isAdmin && (
                <div className="absolute top-4 right-4 z-30">
                  <button
                    onClick={(e) => {
                      e.stopPropagation();
                      setOpenMenuId(openMenuId === photo.id ? null : photo.id);
                    }}
                 className="bg-white/30 backdrop-blur-md p-2 rounded-full shadow-sm hover:bg-white/60 transition-all duration-300 cursor-pointer text-[#5c2d2d] border border-white/40"
>
                    <svg
                      width="20"
                      height="20"
                      fill="currentColor"
                      viewBox="0 0 16 16"
                    >
                      <path d="M9.5 13a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0zm0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0zm0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0z" />
                    </svg>
                  </button>
                  {openMenuId === photo.id && (
                    <div className="absolute right-0 mt-2 w-40 bg-white rounded-lg shadow-xl py-1 z-40">
                      <button
                        onClick={() => openEditModal(photo)}
                        className="w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 flex items-center gap-2"
                      >
                        <span>✏️</span> Modifica
                      </button>
                      <button
                        onClick={() => handleDelete(photo.id)}
                        className="w-full text-left px-4 py-2 text-sm text-red-600 hover:bg-red-50 flex items-center gap-2"
                      >
                        <span>🗑️</span> Rimuovi
                      </button>
                    </div>
                  )}
                </div>
              )}
              <div className="relative p-1 bg-[#a64332] rounded-lg shadow-2xl">
                <img
                  src={`http://localhost:8080${photo.imageUrl}`}
                  alt={photo.title}
                  className="w-full aspect-square object-cover rounded-md transition-transform duration-700 group-hover:scale-[1.02]"
                />
              </div>
              <div className="mt-4 text-center">
                <p className="text-[#f4f1ea] font-serif italic text-xl uppercase tracking-widest">
                  {photo.title}
                </p>
                <span className="text-white/40 text-[10px] uppercase tracking-[3px]">
                  {photo.categories?.name}
                </span>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
