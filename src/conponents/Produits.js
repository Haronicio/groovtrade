import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Card from './Card';
const Produits = () => {
    const [produits, setProduits] = useState(null);
    var username = "dbadmin";
    var password = "admin";
    const token = `${username}:${password}`;
    const encodedToken = btoa(token);
    const session_url = "http://127.0.0.1:8080/";
    var config = {
        method: 'get',
        url: session_url,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Access-Control-Allow-Origin': '*',
            'Authorization': 'Basic ' + encodedToken
        }
    }

    useEffect(() => {
        axios(config)
            //.get("http://127.0.0.1:9090/api/commander/produits")
            .then(function (res) {
                setProduits(res.data);
                console.log(res.data);
            })
    }, []);
    return (
        <div>
            <ul>
                <h1>{token}</h1>
                <h1>{encodedToken}</h1>

                {produits}

            </ul>
        </div>
    );
};

export default Produits;