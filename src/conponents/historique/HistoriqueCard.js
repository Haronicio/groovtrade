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

    var prixTotal = 0;
    var nbrArticle = 0;
    nbrArticle = historique.produits.reduce((acc,panierItem)=>acc+panierItem.quantite,0);
    prixTotal = historique.produits.reduce((acc,panierItem)=>acc+(panierItem.produit.prix * panierItem.quantite),0);
    return (
        <div style={divStyle2}>
            <br/>
            <h1>historiqueid: {historique.historiqueid}</h1>
            <h1>date: {historique.date}</h1>
            <h1>produits: </h1>
            <div style={divStyle}>
                {historique.produits.map((produit,index)=> 
                    <div>
                        <ProduitCard produit={produit.produit}/>
                        <p>quantite:{produit.quantite}</p>    
                    </div>  
                )}
            </div>
            <br/>
            <h1>Prix Total: {prixTotal}</h1>
            <br/>
            <h3>nombre d'article: {nbrArticle}</h3>
        </div>
    );
};

export default HistoriqueCard;