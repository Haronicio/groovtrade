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
    const [quantite,SetQuantite] = useState(1);
    const [filtre,setFiltre] = useState("");
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
    //filter

    if(newProduit){
        return <AddProduit/>
    }else{
    return (
        <div>
            <div>
                <p>filtre</p>
                  <select name="type" id="type-select" onChange={(e)=>setFiltre(e.target.value)}>
                     <option value="">--fitrer par type--</option>
                     <option value="CD">CD</option>
                     <option value="K7">K7</option>
                     <option value="VINYLE33">VINYLE33</option>
                     <option value="VINYLE45">VINYLE45</option>
                  </select>
            </div>
            <div style={divStyle}>
        
                {produits.map((produit, index) => 
                {
                    let handleClick=()=>{
                        axios.post("http://127.0.0.1:8080/api/produits/addPanier",{
                            "produitid":produit.produitid,
                            "quantite":quantite
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
                    if(filtre !== ""){
                        if(produit.type === filtre){
                            return (
                                <div className="produit-container">
                                <ProduitCard key={produit.produitid} produit={produit} />
                                <p>Quantité: <input onChange={(e) => SetQuantite(e.target.value)} type='number' defaultValue={1} /></p>
                                <button onClick={handleClick}>Ajouter au panier</button>
                              </div>
                            )
                        }
                    }else{
                        return (
                            <div>
                                <ProduitCard key={produit.produitid} produit={produit} />
                                <p>Quantité: <input onChange={(e)=>SetQuantite(e.target.value)} type='number' defaultValue={1}/></p>
                                <button onClick={handleClick}>Ajouter au panier</button>
                            </div>
                        )
                    }
                    }
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