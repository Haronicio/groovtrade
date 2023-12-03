import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { header } from '../requete/header';
import ProduitCard from '../produit/ProduitCard';

const Panier = () => {
    const [produits, setProduits] = useState([]);    
    useEffect(() => {
        axios(header.getPanier())
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

export default Panier;