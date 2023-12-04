import React from 'react';
import { NavLink } from 'react-router-dom';

const Navigation = () => {
    return (
        <div>
            <div className="navigation">
                <ul>
                    <NavLink to="/" className={(x)=>x.isActive?"nav-active":""}>
                        <li>Profil</li>
                    </NavLink>
                    <NavLink to="/produits" className={(x)=>x.isActive?"nav-active":""}>
                        <li>Produits</li>
                    </NavLink>
                    <NavLink to="/panier" className={(x)=>x.isActive?"nav-active":""}>
                        <li>Panier</li>
                    </NavLink>
                    <NavLink to="/historique" className={(x)=>x.isActive?"nav-active":""}>
                        <li>Historique</li>
                    </NavLink>
                    <NavLink to="/connexion" className={(x)=>x.isActive?"nav-active":""}>
                        <li>Connexion</li>
                    </NavLink>
                    <NavLink to="/register" className={(x)=>x.isActive?"nav-active":""}>
                        <li>S'inscrire</li>
                    </NavLink>
                    <NavLink to="/deconnecter" className={(x)=>x.isActive?"nav-active":""}>
                        <li>Déconnecter</li>
                    </NavLink>
                </ul>
            </div>
        </div>
    );
};

export default Navigation;