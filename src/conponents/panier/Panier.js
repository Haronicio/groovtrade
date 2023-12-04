import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { header } from '../requete/header';
import ProduitCard from '../produit/ProduitCard';
import { authtificationService } from '../authentification/authentificationService';


const Panier = () => {
    const [produits, setProduits] = useState([]);    
    useEffect(() => {
        axios(header.getPanier())
            .then(function (res) {
                setProduits(res.data);
            })
    },[]);
    let handleClick=()=>{
        axios.post("http://127.0.0.1:8080/api/produits/addHistorique",{},{
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': 'Basic ' + authtificationService.getToken()
            }
        }).then(()=>{
            alert("produit validé !");
            window.location.reload();
            }).catch((erreur)=>{
            alert(erreur);
        });

    }
    const divStyle = {
        display: 'flex',
        flexWrap: 'wrap'
    };
    if(produits.length !== 0){
        return (
            <div style={divStyle}>
                {produits.map((produit, index) => (
                   <ProduitCard produit={produit} />
                ))}
                <button onClick={handleClick}>Valider</button>
            </div>
        );
    }else{
        return (
            <div>
                <h1>Votre panier est vide</h1>
            </div>
        )
    }
};

export default Panier;