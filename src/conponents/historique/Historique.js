import axios from 'axios';
import React, { useEffect, useMemo, useState } from 'react';
import { header } from '../requete/header';
import HistoriqueCard from './HistoriqueCard';

const Historique = () => {
  const [selectedDate, setSelectedDate] = useState(null);
  const [historiques, setHistoriques] = useState([]);
  useEffect(() => {
    axios(header.getHistorique())
      .then(function (res) {
        console.log(res.data);
        setHistoriques(res.data);
      })
  }, []);
  const filteredHistorique = useMemo(() => {
    if (!selectedDate) {
      return historiques;
    }

    return historiques.filter((entry) => entry.date === selectedDate);
  }, [historiques, selectedDate]);
  const resetFilter = () => {
    setSelectedDate(null);
  };
  return (
    <div>
      <div className="filter-container">
        <div>
          <div>
            <label>Sélectionner une date :</label>
            <input
              type="date"
              value={selectedDate}
              onChange={(e) => setSelectedDate(e.target.value)}
            />
          </div>
          <button onClick={resetFilter}>Réinitialiser le filtre</button>
        </div>
      </div>
      <div className='historique-container'>
        <div className="historique-list">
          {filteredHistorique.sort((a, b) => {
            const dateA = new Date(a.date);
            const dateB = new Date(b.date);

            // Trie du plus récent au plus ancien
            if (dateA > dateB) return -1;
            if (dateA < dateB) return 1;

            // Si les dates sont égales, comparez par les identifiants
            return b.historiqueid - a.historiqueid;
          }).map((historique, index) =>

            <HistoriqueCard key={historique.historiqueid} historique={historique} className="historique-card" />


          )}
        </div>
      </div>
    </div>


  );
};

export default Historique;