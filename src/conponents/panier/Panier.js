import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { header } from '../requete/header';
import ProduitCard from '../produit/ProduitCard';
import { authtificationService } from '../authentification/authentificationService';

const Panier = () => {
    useEffect(() => {
        axios(header.getPanier())
            .then(function (res) {
                setProduits(res.data);
                console.log(res.data);
            });
    },[]);
    const [produits, setProduits] = useState([]);  
    var prixTotal = 0;
    var nbrArticle = 0;
    nbrArticle = produits.reduce((acc,panierItem)=>acc+panierItem.quantite,0);
    prixTotal = produits.reduce((acc,panierItem)=>acc+(panierItem.produit.prix * panierItem.quantite),0);
    

    let handleAdd=()=>{
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
            <div>
                <div style={divStyle}>

                    {produits.map((panierItem, index) => 
                    {
                        let handleRemoveOne=()=>{
                            axios.post("http://127.0.0.1:8080/api/produits/removeOnePanier",{
                                "produitid":panierItem.produit.produitid,
                                "quantite":panierItem.quantite
                            },{
                                headers: {
                                    'Content-Type': 'application/json',
                                    'Access-Control-Allow-Origin': '*',
                                    'Authorization': 'Basic ' + authtificationService.getToken()
                                }
                            }).then(()=>
                                window.location.reload()
                            ).catch((erreur)=>{
                                alert(erreur);
                            });}
                            let handleRemove=()=>{
                                axios.post("http://127.0.0.1:8080/api/produits/removePanier",{
                                    "produitid":panierItem.produit.produitid,
                                    "quantite":panierItem.quantite
                                },{
                                    headers: {
                                        'Content-Type': 'application/json',
                                        'Access-Control-Allow-Origin': '*',
                                        'Authorization': 'Basic ' + authtificationService.getToken()
                                    }
                                }).then(()=>
                                    window.location.reload()
                                ).catch((erreur)=>{
                                    alert(erreur);
                                });
                            }
                    return (
                        <div>
                        <ProduitCard produit={panierItem.produit} />
                        <button  onClick={handleRemoveOne}>quantité -1</button>
                        <br/>
                        <button onCanPlay={handleRemove}>supprime</button>
                        <p>quantité: {panierItem.quantite}</p>
                    </div>
                    );}
                    )}
                    <button onClick={handleAdd}>Valider</button>
                </div>
                <br/>
                <h1>prix total: {prixTotal} €</h1>
                <h3>nombre d'article: {nbrArticle}</h3>
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