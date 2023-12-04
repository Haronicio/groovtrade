import { useState } from 'react';
import { authtificationService } from '../authentification/authentificationService';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Register = () => {
    var navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState("");

    const handle = (event) => {
        event.preventDefault();
        const token = btoa(`${username}:${password}`);
        axios.post("http://127.0.0.1:8080/api/produits/register",
        {
            "username":username,
            "password":password,
            "email":email
        },{})
        .then(()=>{
            alert("inscription reussi !");
            authtificationService.setToken(token);
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
            <label>email:<input type="email" id="password" onChange={(e)=>setEmail(e.target.value)}/></label>
            <br/>

            <input type="submit" value="S'inscrire"/>
        </form>
    ):(
        <div>
            <h1>Vous êtes conncté!</h1>
                <button onClick={handleClick}>ok</button>
        </div>
    );
};

export default Register;