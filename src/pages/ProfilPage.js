import React, { useEffect } from 'react';
import Navigation from '../conponents/Navigation';
import Profil from '../conponents/profil/Profil';
import { useNavigate } from 'react-router-dom';
import { authtificationService } from '../conponents/authentification/authentificationService';


const ProfilPage = () => {
    const navigate = useNavigate();
    useEffect(() => {  
        if(!authtificationService.islogged()){
            navigate('/connexion');
        }
    }, [navigate]);
    return (authtificationService.islogged()&&
        <div>
            <Navigation />
            <Profil/>
        </div>
    );
};

export default ProfilPage;