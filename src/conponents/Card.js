import React from 'react';

const Card = (produit) => {
    return (
        <li>
            <div>
                <h1>{console.log(produit)}</h1>
                <h1>nom: {produit.produit.nom}</h1>
                <h2>prix: {produit.produit.prix}</h2>
                <h3>description: {produit.produit.description}</h3>
            </div>
        </li>
    );
};

export default Card;