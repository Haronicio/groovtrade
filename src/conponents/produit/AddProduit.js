import axios from 'axios';
import React, { useState } from 'react';
import { authtificationService } from '../authentification/authentificationService';


const AddProduit = () => {
    const [prix, setPrix] = useState(0);
    const [description, setDescription] = useState("");
    const [type, setType] = useState("");
    const [nom, setNom] = useState("");
    const [artiste, setArtiste] = useState(0);
    const [album, setAlbum] = useState("");
    const [annee, setAnnee] = useState(0);
    const [genres, setGenres] = useState("");
    let handleClick = () => {
        axios.post("http://127.0.0.1:8080/api/produits/addProduit",
            {
                "prix": prix,
                "description": description,
                "type": type,
                "meta": {
                    "nom": nom,
                    "artiste": artiste,
                    "album": album,
                    "annee": annee,
                    "genres": genres
                }
            }, {
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Authorization': 'Basic ' + authtificationService.getToken()
            }
        }).then(() => {
            alert("produit ajouté");
            window.location.reload();
        }
        ).catch((erreur) => {
            alert(erreur);
        });
    }
    const divStyle = {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        flexWrap: 'wrap'
    }
    return (
        <div style={divStyle}>
            <div>
                <h1>Ajouter un nouveau produit</h1>

                <h2>prix:<input type="number" id="prix" onChange={(e) => setPrix(e.target.value)} /></h2>
                <h2>description:<input type="text" id="description" onChange={(e) => setDescription(e.target.value)} /></h2>
                <h2>type:
                    <select name="type" id="type-select" onChange={(e) => setType(e.target.value)}>
                        <option value="">--Please choose an option--</option>
                        <option value="CD">CD</option>
                        <option value="K7">K7</option>
                        <option value="VINYLE33">VINYLE33</option>
                        <option value="VINYLE45">VINYLE45</option>
                    </select>
                </h2>
                <h2>nom:<input type="text" id="nom" onChange={(e) => setNom(e.target.value)} /></h2>
                <h2>artiste:<input type="text" id="artiste" onChange={(e) => setArtiste(e.target.value)} /></h2>
                <h2>album:<input type="text" id="album" onChange={(e) => setAlbum(e.target.value)} /></h2>
                <h2>annee:<input type="number" id="annee" onChange={(e) => setAnnee(e.target.value)} /></h2>
                <h2>genres:<input type="text" id="genres" onChange={(e) => setGenres(e.target.value)} /></h2>
                <button onClick={handleClick}>Ajouter ce produit</button>
                <br />
                <br />
                <button onClick={() => { window.location.reload(); }}>Annuler</button>
            </div>
        </div>
    );
};

export default AddProduit;