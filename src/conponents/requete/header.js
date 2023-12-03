import axios from "axios";
import { authtificationService } from "../authentification/authentificationService"
import { useState } from "react";

let getRequest=(path)=>{
    var config = {
        method: 'get',
        url: path,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Access-Control-Allow-Origin': '*',
            'Authorization': 'Basic ' + authtificationService.getToken()
        }
    }
    return config;
}

let getProduits=()=>{
    return getRequest("http://127.0.0.1:8080/api/produits/liste");
}
let getPanier=()=>{
    return getRequest("http://127.0.0.1:8080/api/produits/panier");
}
let getHistorique=()=>{
    return getRequest("http://127.0.0.1:8080/api/produits/historique");
}

export const header = {
    getProduits,getPanier,getHistorique
}