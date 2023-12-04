import React from 'react';
import ProduitCard from '../produit/ProduitCard';

const HistoriqueCard = ({historique}) => {
    const divStyle = {
        display: 'flex',
        flexWrap: 'wrap'
    };
    const divStyle2 ={
        borderBottom: '4px solid #82e0f9',
        borderTop:'4px solid #82e0f9'
    }
    return (
        <div style={divStyle2}>
            <br/>
            <h1>historiqueid: {historique.historiqueid}</h1>
            <h1>date: {historique.date}</h1>
            <h1>produits: </h1>
            <div style={divStyle}>
                {historique.produits.map((produit,index)=> 
                    <ProduitCard produit={produit}/>      
                )}
            </div>
            <br/>
        </div>
    );
};

export default HistoriqueCard;