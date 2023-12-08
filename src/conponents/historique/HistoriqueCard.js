import React from 'react';
import ProduitCard from '../produit/ProduitCard';

const HistoriqueCard = ({ historique }) => {
  // const divStyle = {
  //     display: 'flex',
  //     flexWrap: 'wrap'
  // };
  // const divStyle2 ={
  //     borderBottom: '4px solid #82e0f9',
  //     borderTop:'4px solid #82e0f9'
  // }

  var prixTotal = 0;
  var nbrArticle = 0;
  nbrArticle = historique.produits.reduce((acc, panierItem) => acc + panierItem.quantite, 0);
  prixTotal = historique.produits.reduce((acc, panierItem) => acc + (panierItem.produit.prix * panierItem.quantite), 0);
  return (
    <div className="historique-container">
      <div>
        <br />
        <h1>Commande nº {historique.historiqueid}</h1>
        <h1>date: {historique.date}</h1>
        <h1>produits: </h1>
        <div className="produits-container">
          {historique.produits.sort((a, b) => a.produit.produitid - b.produit.produitid).map((produit, index) => (
            <div className='div-produit-details'>
              <div className="produit-details" key={index}>

                <ProduitCard produit={produit.produit} />
                <p>quantite:{produit.quantite}</p>
                <br/>
                <div >
                  {produit.commentaire&&
                  <div>
                    <div>
                      Commentaire:
                    </div>
                    <div>{produit.commentaire}</div>
                  </div>
                 }
                </div>

              </div>
            </div>
          ))}
        </div>
        <br />
        <h1>Prix Total: {prixTotal}</h1>
        <br />
        <h3>Adresse de livraison: {historique.livraison}</h3>
        <br />
        <h3>nombre d'article: {nbrArticle}</h3>
      </div>
    </div>
  );
};

export default HistoriqueCard;