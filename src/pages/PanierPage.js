import React, { useEffect } from 'react';
import Navigation from '../conponents/Navigation';
import Panier from '../conponents/panier/Panier';
import { authtificationService } from '../conponents/authentification/authentificationService';
import { useNavigate } from 'react-router-dom';
const PanierPage = () => {
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
                <Panier/>
                
            </div>
        );
    }
};

export default PanierPage;