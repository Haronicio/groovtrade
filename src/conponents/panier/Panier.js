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
    const [nom, setNom] = useState('');
    const [adresse, setAdresse] = useState('');
    const [ville, setVille] = useState('');
    const [codePostal, setCodePostal] = useState('');
    const [pays, setPays] = useState('');

    const handleNomChange = (e) => setNom(e.target.value);
    const handleAdresseChange = (e) => setAdresse(e.target.value);
    const handleVilleChange = (e) => setVille(e.target.value);
    const handleCodePostalChange = (e) => setCodePostal(e.target.value);
    const handlePaysChange = (e) => setPays(e.target.value);


    const [produits, setProduits] = useState([]);
    const [passerLaCommande, setPasserLaCommande] = useState(false);
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
    let handleAdd = (informationsLivraison) => {
        axios.post("http://127.0.0.1:8080/api/produits/addHistorique", { "info": informationsLivraison }, {
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
            handleLivaison();
        }).catch((erreur) => {
            alert(erreur);
        });
    }
    let handleLivaison = () => {
        setPasserLaCommande(passerLaCommande ? false : true);

    }
    const handleSubmit = (e) => {
        e.preventDefault();

        // Concaténer toutes les informations du formulaire
        const informationsLivraison = `Nom: ${nom}, Adresse: ${adresse}, Ville: ${ville}, Code Postal: ${codePostal}, Pays: ${pays}`;
        handleAdd(informationsLivraison);
    };

    //commentaire
    const [commentaires, setCommentaires] = useState(produits.map((panierItem) => "panierItem.commentaire"));

    const handleCommentaireChange = (index, e) => {
        const nouveauxCommentaires = [...commentaires];
        nouveauxCommentaires[index] = e.target.value;
        setCommentaires(nouveauxCommentaires);

        
    };

    const handleModifierCommentaire = (index) => {
        console.log(`Nouveau commentaire pour produit ${index}: ${commentaires[index]}`);
        console.log(produits[index].produit.produitid);
        axios.post("http://127.0.0.1:8080/api/produits/addCommentaire",{
            "produitid":produits[index].produit.produitid,
            "commentaire":commentaires[index]
        },{
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': 'Basic ' + authtificationService.getToken()
            }
        }).then(
            toast.success("Commentaire modifié !", {
            position: toast.POSITION.BOTTOM_RIGHT})
        ).catch((erreur)=>{
            alert(erreur);
        });


    };

    return (
        <div className="panier">
            {passerLaCommande ?
                <div>
                    <h1>Information de la Livraison</h1>
                    <div>
                        <form className="delivery-form" onSubmit={handleSubmit}>
                            <label>
                                Nom:
                                <input type="text" value={nom} onChange={handleNomChange} />
                            </label>
                            <br />
                            <label>
                                Adresse:
                                <input type="text" value={adresse} onChange={handleAdresseChange} />
                            </label>
                            <br />
                            <label>
                                Ville:
                                <input type="text" value={ville} onChange={handleVilleChange} />
                            </label>
                            <br />
                            <label>
                                Code Postal:
                                <input type="text" value={codePostal} onChange={handleCodePostalChange} />
                            </label>
                            <br />
                            <label>
                                Pays:
                                <input type="text" value={pays} onChange={handlePaysChange} />
                            </label>
                            <br />
                            <button type="submit">Valider</button>
                            <br />
                            <br />
                            <button onClick={handleLivaison}>Annuler</button>
                        </form>
                    </div>

                </div> : (
                    produits.length !== 0 ? (
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

                                        <div className="div-produit-details">
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

                                                    <label>
                                                        Commentaire:
                                                        <input
                                                            type="text"
                                                            defaultValue={panierItem.commentaire}
                                                            onChange={(e) => handleCommentaireChange(index, e)}
                                                        />
                                                    </label>
                                                    <br/>
                                                    <button className="button" onClick={() => handleModifierCommentaire(index)}>
                                                        Modifier Commentaire
                                                    </button>
                                                    <br/>
                                                    <button onClick={handleRemove} className="button supprimer-button">
                                                        Supprimer
                                                    </button>

                                                </div>
                                            </div>
                                        </div>
                                    );
                                })}
                            </div>

                            <button onClick={handleLivaison} className="valider-button">Passer la commande</button>
                            <br />
                            <h1 className="prix-total">prix total: {prixTotal} €</h1>
                            <h3>nombre d'article: {nbrArticle}</h3>
                        </>
                    ) : (
                        <div className="empty-cart-message">
                            <h1>Votre panier est vide</h1>
                        </div>
                    ))}
            <ToastContainer />
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