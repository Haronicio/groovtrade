import React, { useState,useEffect } from 'react';
import Navigation from '../conponents/Navigation';
import { useNavigate } from 'react-router-dom';
import Produits from '../conponents/produit/Produits';
import { authtificationService } from '../conponents/authentification/authentificationService';

const ProduitsPage = ({token}) => {
    const navigate = useNavigate();
    useEffect(() => {  
        if(!authtificationService.islogged()){
            navigate('/connexion');
        }
    }, [navigate]);
    
    if(authtificationService.islogged()){
        return(
            <div>
                <Navigation />
                <Produits />
            </div>
        );
    }

};

export default ProduitsPage;