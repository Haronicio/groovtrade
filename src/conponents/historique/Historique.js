import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { header } from '../requete/header';
import HistoriqueCard from './HistoriqueCard';

const Historique = () => {
    
    const [historiques, setHistoriques] = useState([]);    
    useEffect(() => {
        axios(header.getHistorique())
            .then(function (res) {
                console.log(res.data);
                setHistoriques(res.data);
            })
    },[]);  



    return (
        <div>
            {historiques.map((historique, index) => 
                <HistoriqueCard key={historique.historiqueid} historique={historique}/>
            )};
        </div>

        
    );
};

export default Historique;