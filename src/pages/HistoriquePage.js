import React, { useEffect } from 'react';
import Navigation from '../conponents/Navigation';
import Historique from '../conponents/historique/Historique';
import { authtificationService } from '../conponents/authentification/authentificationService';
import { useNavigate } from 'react-router-dom';
const HistoriquePage = () => {
    const navigate = useNavigate();
    useEffect(() => {  
        if(!authtificationService.islogged()){
            navigate('/connexion');
        }
    }, [navigate]);

    if(authtificationService.islogged()){
        return (
            <div>
                <Navigation/>
                <Historique />
            </div>
        );
    }
};

export default HistoriquePage;