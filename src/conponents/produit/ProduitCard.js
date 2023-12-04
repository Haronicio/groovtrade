import React, { useState } from 'react';

const ProduitCard = ({produit}) => {
    

    var divStyle ={  
        width: '150px'
        
    }
    return (
        <div >
            <div style={divStyle}>
                <br/>
                <p><b>Vendeur :</b>{produit.utilisateurId}</p>
                <p><b>Description :</b>{produit.description}</p>
                <p><b>Prix :</b>{produit.prix} </p>
                <p><b>Type :</b>{produit.type} </p>
                <ul>
                    <li><b>Album :</b> {produit.meta.album}</li>
                    <li><b>Artiste :</b> {produit.meta.artiste}</li>
                    <li><b>Année :</b> {produit.meta.annee}"</li>
                    <li><b>Genres :</b> {produit.meta.genres}</li>
                </ul>

            </div>
        </div>
    );
};

export default ProduitCard;