import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { header } from '../requete/header';
import ProduitCard from '../produit/ProduitCard';
import { authtificationService } from '../authentification/authentificationService';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const Panier = () => {
    useEffect(() => {
        axios(header.getPanier())
            .then(function (res) {
                setProduits(res.data);
            });
    }, []);
    const [produits, setProduits] = useState([]);

    const updateQuantite = (produitidToUpdate, newQuantite) => {
        // Create a new array with the updated quantite
        const updatedProduits = produits.map(produit => {
            if (produit.produit.produitid === produitidToUpdate) {
                // Update the quantite of the matching product
                return {
                    ...produit,
                    quantite: newQuantite,
                };
            }
            return produit;
        }).filter(produit => produit.quantite > 0); // Filter out items with quantite <= 0

        // Update the state with the new array
        setProduits(updatedProduits);
    };

    var prixTotal = 0;
    var nbrArticle = 0;
    nbrArticle = produits.reduce((acc, panierItem) => acc + panierItem.quantite, 0);
    prixTotal = produits.reduce((acc, panierItem) => acc + (panierItem.produit.prix * panierItem.quantite), 0);
    let handleAdd = () => {
        axios.post("http://127.0.0.1:8080/api/produits/addHistorique", {}, {
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': 'Basic ' + authtificationService.getToken()
            }
        }).then(() => {
            setProduits([]);
            toast.success("panier validé !", {
                position: toast.POSITION.BOTTOM_RIGHT
            })
        }).catch((erreur) => {
            alert(erreur);
        });
    }


    return (
        <div className="panier">

        {produits.length !== 0 ? (
          <>
            <div className="panier-item">
              {produits.sort((a, b) => a.produit.produitid - b.produit.produitid).map((panierItem, index) => {
                let handleRemoveOne = () => {
                    axios.post("http://127.0.0.1:8080/api/produits/removeOnePanier", {
                                "produitid": panierItem.produit.produitid
                            }, {
                                headers: {
                                    'Content-Type': 'application/json',
                                    'Access-Control-Allow-Origin': '*',
                                    'Authorization': 'Basic ' + authtificationService.getToken()
                                }
                            }).then(() => {
                                updateQuantite(panierItem.produit.produitid, panierItem.quantite - 1)
                            }
                            ).catch((erreur) => {
                                alert(erreur);
                            });
                }
                let handleRemove = () => {
                    axios.post("http://127.0.0.1:8080/api/produits/removePanier", {
                        "produitid": panierItem.produit.produitid,
                        "quantite": panierItem.quantite
                    }, {
                        headers: {
                            'Content-Type': 'application/json',
                            'Access-Control-Allow-Origin': '*',
                            'Authorization': 'Basic ' + authtificationService.getToken()
                        }
                    }).then(() =>
                        updateQuantite(panierItem.produit.produitid, 0)
                    ).catch((erreur) => {
                        alert(erreur);
                    });
                }
                let handleAdd = () => {
                    axios.post("http://127.0.0.1:8080/api/produits/addPanier", {
                        "produitid": panierItem.produit.produitid,
                        "quantite": "1"
                    }, {
                        headers: {
                            'Content-Type': 'application/json',
                            'Access-Control-Allow-Origin': '*',
                            'Authorization': 'Basic ' + authtificationService.getToken()
                        }
                    }).then(
                        updateQuantite(panierItem.produit.produitid, panierItem.quantite + 1)

                    ).catch((erreur) => {
                        alert(erreur);
                    });
                }
                return (
                  <div className="produit-details" key={index}>
                    <ProduitCard produit={panierItem.produit} />
                    <div className="actions-container">
                      <div className="quantity-container">
                        <button onClick={handleRemoveOne} className="button">
                          -1
                        </button>
                        <p>Quantité: {panierItem.quantite}</p>
                        <button onClick={handleAdd} className="button">
                          +1
                        </button>
                      </div>
                      <button onClick={handleRemove} className="button supprimer-button">
                        Supprimer
                      </button>
                    </div>
                  </div>
                );
              })}
            </div>
    
            <button onClick={handleAdd} className="valider-button">Valider</button>
            <br />
            <h1 className="prix-total">prix total: {prixTotal} €</h1>
            <h3>nombre d'article: {nbrArticle}</h3>
          </>
        ) : (
          <div className="empty-cart-message">
            <h1>Votre panier est vide</h1>
          </div>
        )}
        <ToastContainer/>
      </div>
    );










    // if (produits.length !== 0) {
    //     return (
    //         <div className="panier" >

    //             <div className="panier-item" >

    //                 {produits.sort((a, b) => a.produit.produitid - b.produit.produitid).map((panierItem, index) => {
    //                     let handleRemoveOne = () => {
    //                         axios.post("http://127.0.0.1:8080/api/produits/removeOnePanier", {
    //                             "produitid": panierItem.produit.produitid
    //                         }, {
    //                             headers: {
    //                                 'Content-Type': 'application/json',
    //                                 'Access-Control-Allow-Origin': '*',
    //                                 'Authorization': 'Basic ' + authtificationService.getToken()
    //                             }
    //                         }).then(() => {
    //                             updateQuantite(panierItem.produit.produitid, panierItem.quantite - 1)
    //                         }
    //                         ).catch((erreur) => {
    //                             alert(erreur);
    //                         });
    //                     }
    //                     let handleRemove = () => {
    //                         axios.post("http://127.0.0.1:8080/api/produits/removePanier", {
    //                             "produitid": panierItem.produit.produitid,
    //                             "quantite": panierItem.quantite
    //                         }, {
    //                             headers: {
    //                                 'Content-Type': 'application/json',
    //                                 'Access-Control-Allow-Origin': '*',
    //                                 'Authorization': 'Basic ' + authtificationService.getToken()
    //                             }
    //                         }).then(() =>
    //                             updateQuantite(panierItem.produit.produitid, 0)
    //                         ).catch((erreur) => {
    //                             alert(erreur);
    //                         });
    //                     }
    //                     let handleAdd = () => {
    //                         axios.post("http://127.0.0.1:8080/api/produits/addPanier", {
    //                             "produitid": panierItem.produit.produitid,
    //                             "quantite": "1"
    //                         }, {
    //                             headers: {
    //                                 'Content-Type': 'application/json',
    //                                 'Access-Control-Allow-Origin': '*',
    //                                 'Authorization': 'Basic ' + authtificationService.getToken()
    //                             }
    //                         }).then(
    //                             updateQuantite(panierItem.produit.produitid, panierItem.quantite + 1)

    //                         ).catch((erreur) => {
    //                             alert(erreur);
    //                         });

    //                     }
    //                     return (
    //                         <div className="produit-details">
    //                             <ProduitCard produit={panierItem.produit} />

    //                             <div className="actions-container">
    //                                 <div className="quantity-container">
    //                                     <button onClick={handleRemoveOne} className="button">
    //                                         -1
    //                                     </button>
    //                                     <p>Quantité: {panierItem.quantite}</p>
    //                                     <button onClick={handleAdd} className="button">
    //                                         +1
    //                                     </button>
    //                                 </div>

    //                                 <button onClick={handleRemove} className="button supprimer-button">
    //                                     Supprimer
    //                                 </button>

    //                             </div>
    //                         </div>
    //                     );
    //                 }
    //                 )}

    //             </div>
    //             <button onClick={handleAdd} className="valider-button">Valider</button>
    //             <br />
    //             <h1 className="prix-total">prix total: {prixTotal} €</h1>
    //             <h3>nombre d'article: {nbrArticle}</h3>

    //         </div>
    //     );
    // } else {
    //     return (
    //         <div className="empty-cart-message">
    //             <h1>Votre panier est vide</h1>
    //         </div>
    //     )
    // }
};

export default Panier;