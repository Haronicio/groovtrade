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
let postRequest=(path)=>{
    var config = {
        method: 'post',
        url: path,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Access-Control-Allow-Origin': '*',
            'Authorization': 'Basic ' + authtificationService.getToken()
        }
    }
    return config;
}
//get
let getProduits=()=>{
    return getRequest("http://127.0.0.1:8080/api/produits/liste");
}
let getPanier=()=>{
    return getRequest("http://127.0.0.1:8080/api/produits/panier");
}
let getHistorique=()=>{
    return getRequest("http://127.0.0.1:8080/api/produits/historique");
}
let getProfil=()=>{
    return getRequest("http://127.0.0.1:8080/api/produits/");
}

//post
let postAddPanier=()=>{
    return postRequest("http://127.0.0.1:8080/api/produits/addPanier");
}
let postAddHistorique=()=>{
    return postRequest("http://127.0.0.1:8080/api/produits/addHistorique");
}
let postAddProduit=()=>{
    return postRequest("http://127.0.0.1:8080/api/produits/addProduit");
}

export const header = {
    getProduits,getPanier,getHistorique,postAddPanier,postAddHistorique,postAddProduit,getProfil
}