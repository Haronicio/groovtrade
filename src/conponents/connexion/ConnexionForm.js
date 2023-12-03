import React, { useEffect } from 'react';
import { useState } from 'react';
import { authtificationService } from '../authentification/authentificationService';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const ConnexionForm = () => {
    var navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const handle = (event) => {
        event.preventDefault();
        const session_url = "http://127.0.0.1:8080/api/produits";
        const token = btoa(`${username}:${password}`);
        var config = {
            method: 'get',
            url: session_url,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Access-Control-Allow-Origin': '*',
                'Authorization': 'Basic ' + token
            }
        }

        axios(config)
            .then((res) => {
                if(res.status === 200){
                    authtificationService.setToken(token);
                }
                window.location.reload();
            }).catch((erreur)=>{
                alert(erreur);
            });
    }
    function handleClick(){
        navigate("/");
    }
    return (!authtificationService.islogged())?(
        <form onSubmit={handle}>
            <label>Username:<input type="text" id="username" onChange={(e)=>setUsername(e.target.value)}/></label>
            <br/>
            <label>Password:<input type="password" id="password" onChange={(e)=>setPassword(e.target.value)}/></label>
            <br/>
            <input type="submit" value="Connecter"/>
        </form>
    ):(
        <div>
            <h1>Vous êtes conncté!</h1>
                <button onClick={handleClick}>ok</button>
        </div>
    );


};

export default ConnexionForm;