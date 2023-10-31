import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Card from './Card';
const Produits = () => {
    const [produits, setProduits] = useState([]);
    useEffect(() => {
        axios
            .get("http://127.0.0.1:8080/api/commander/produits")
            .then((res) => setProduits(res.data))
            .catch((error)=>console.log(error))
    }, []);
    return (
        <div>
            <ul>
                {produits.map((produit) => (
                    <Card produit={produit}/>
                ))}
            </ul>
        </div>
    );
};

export default Produits;