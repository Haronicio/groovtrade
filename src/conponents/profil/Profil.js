import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { header } from '../requete/header';


const Profil = () => {

    const [profil, setProfil] = useState([]);

    useEffect(() => {
        axios(header.getProfil())
            .then(function (res) {
                setProfil(res.data);
                console.log(res.data);
            }).catch((error)=>{
                alert(error);
            })
    },[]);
    const divStyle={
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        flexWrap: 'wrap'
    }
    return (
        <div style={divStyle}>
            <div>
                <h1>userid: {profil.userid}</h1>
                <h1>username: {profil.username}</h1>
                <h1>email : {profil.email}</h1>
                <h1>role: {profil.role}</h1>
            </div>
        </div>
    );
};

export default Profil;