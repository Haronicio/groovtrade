import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { authtificationService } from '../authentification/authentificationService';

const ProduitCard = ({produit}) => {
    //obtenir img
    const [imageSrc, setImageSrc] = useState('');
    const path =  (produit.imgs.length > 0)?produit.imgs[0].path:"";
    useEffect(() => {
        if(path !== ""){
            axios.post("http://127.0.0.1:8080/api/produits/image", {
                "path":path
            },{
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*',
                    'Authorization': 'Basic ' + authtificationService.getToken()
                }
            })
            .then((response) =>{
                setImageSrc(`data:image/jpeg;base64,${response.data}`);
            })
            .catch(error => console.error('Erreur lors de la récupération de l\'image :', error));
        }
    }, [path]);
    var divStyle ={  
        width: '150px'
    }
    return (
        <div >
            <div style={divStyle}>
                <br/>
                {imageSrc && <img  width='500' height='200' src={imageSrc} alt="imageFailed" />}
                <p><b>Vendeur :</b>{produit.utilisateurId}</p>
                <p><b>Description :</b>{produit.description}</p>
                <p><b>Prix :</b>{produit.prix} €</p>
                <p><b>Type :</b>{produit.type} </p>
                <ul>
                    <li><b>Album :</b> {produit.meta.album}</li>
                    <li><b>Artiste :</b> {produit.meta.artiste}</li>
                    <li><b>Année :</b> {produit.meta.annee}"</li>
                    <li><b>Genres :</b> {produit.meta.genres}</li>
                </ul>

            </div>
        </div>
    );
};

export default ProduitCard;