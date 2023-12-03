import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ProduitCard from "./ProduitCard";
import { authtificationService } from '../authentification/authentificationService';
import { header } from '../requete/header';

const Produits = ({token}) => {
    const [produits, setProduits] = useState([]);    
    useEffect(() => {
        axios(header.getProduits())
            .then(function (res) {
                setProduits(res.data);
            })
    },[]);
    return (
        <div>
            <ul>
                {/* <h1>{token}</h1>
                <h1>{encodedToken}</h1> */}
                {produits.map((produit, index) => (
                   <ProduitCard produit={produit} />
                ))}
            </ul>
        </div>
    );
};

export default Produits;