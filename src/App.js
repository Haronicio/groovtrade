import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import ProduitsPage from './pages/ProduitsPage';
import ConnexionPage from './pages/ConnexionPage';
import DeconnexionPage from './pages/DeconnexionPage';
import PanierPage from './pages/PanierPage';
import HistoriquePage from './pages/HistoriquePage';
const App = () => {
 
    return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/produits" element={<ProduitsPage />} />
          <Route path="/panier" element={<PanierPage />} />
          <Route path="/historique" element={<HistoriquePage/>} />

          <Route path="/connexion" element={<ConnexionPage/>}/>
          <Route path="/deconnecter" element={<DeconnexionPage/>}/> 
        </Routes>
      </BrowserRouter>
    );
};

export default App;