import React, { useState,useEffect } from 'react';
import Navigation from '../conponents/Navigation';

import Produits from '../conponents/produit/Produits';

const ProduitsPage = ({token}) => {

    return (
        <div>
            <Navigation />
            <Produits token={token}/>
        </div>
    );
};

export default ProduitsPage;