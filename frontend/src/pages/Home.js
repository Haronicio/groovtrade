import React from 'react';
import Navigation from '../conponents/Navigation';
import Logo from '../conponents/Logo';
import Produits from '../conponents/Produits';

const Home = () => {
    return (
        <div>
            <Logo />
            <Navigation />
            <Produits />
        </div>
    );
};

export default Home;