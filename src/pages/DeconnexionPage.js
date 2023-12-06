import React from 'react';
import Navigation from '../conponents/Navigation';

import { authtificationService } from '../conponents/authentification/authentificationService';


const DeconnexionPage = () => {
    function handleClick(){
        authtificationService.logout();
        window.location.reload();
    }
    return (authtificationService.islogged())?(
        <div>
            <Navigation/>
            <div className="logout-container">
                <h1>êtes vous sûre de se déconnecter?</h1>
                <button onClick={handleClick}>Oui</button>
            </div>
        </div>
    ):(<div>
        <Navigation/>
        <div className="not-logged-in-container">
            <h1>Vous n'êtes pas encore connecté</h1>
        </div>
        </div>)
};

export default DeconnexionPage;