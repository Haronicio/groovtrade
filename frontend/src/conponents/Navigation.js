import React from 'react';
import { NavLink } from 'react-router-dom';

const Navigation = () => {
    return (
        <div>
            <div className="navigation">
                <ul>
                    <NavLink to="/" className={(x)=>x.isActive?"nav-active":""}>
                        <li>Acceuil</li>
                    </NavLink>
                </ul>
            </div>
        </div>
    );
};

export default Navigation;