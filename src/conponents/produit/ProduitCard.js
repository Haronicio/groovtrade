import React from 'react';

const ProduitCard = ({produit}) => {
    return (
        <div>
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
    );
};

export default ProduitCard;