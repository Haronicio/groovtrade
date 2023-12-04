import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ProduitCard from "./ProduitCard";
import { header } from '../requete/header';
import { authtificationService } from '../authentification/authentificationService';
import AddProduit from './AddProduit';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const Produits = ({token}) => {
    const [newProduit,setNewProdtui] = useState(false);
    const [produits, setProduits] = useState([]);  
    // obtenir liste des produits  
    useEffect(() => {
        axios(header.getProduits())
            .then(function (res) {
                setProduits(res.data);
            })
    },[]);
    const divStyle = {
        display: 'flex',
        flexWrap: 'wrap'
    };
    //action clique sur bouton
        let handleClick=()=>{
        newProduit?setNewProdtui(false):setNewProdtui(true);
    }

    if(newProduit){
        return <AddProduit/>
    }else{
    return (
        <div>
            <div style={divStyle}>
                {produits.map((produit, index) => 
                {
                    let handleClick=()=>{
                        axios.post("http://127.0.0.1:8080/api/produits/addPanier",{
                            "produitid":produit.produitid
                        },{
                            headers: {
                                'Content-Type': 'application/json',
                                'Access-Control-Allow-Origin': '*',
                                'Authorization': 'Basic ' + authtificationService.getToken()
                            }
                        }).then(
                            toast.success("produit ajouté au panier !", {
                            position: toast.POSITION.BOTTOM_RIGHT})
                        ).catch((erreur)=>{
                            alert(erreur);
                        });
                        
                    }
                    return (
                    <div>
                        <ProduitCard key={produit.produitid} produit={produit} />
                        <button onClick={handleClick}>Ajouter au panier</button>
                    </div>
                    )}
                )}
            </div>
            <br/>
            <button onClick={handleClick}>Ajouter un nouveau produit</button>
            <ToastContainer />
        </div>
    );
}
};

export default Produits;