import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { header } from '../requete/header';
import HistoriqueCard from './HistoriqueCard';

const Historique = () => {
    
    const [historiques, setHistoriques] = useState([]);    
    useEffect(() => {
        axios(header.getPanier())
            .then(function (res) {
                setHistoriques(res.data);
            })
    },[]);  
    return (
        <div>
            {historiques.map((historique, index)=>{
                <HistoriqueCard historique={historique}/>
            })};
        </div>
    );
};

export default Historique;