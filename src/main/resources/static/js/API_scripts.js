// Ce script contient les datas et les logiques pour VueJS
// AInsi que les logiques pour cette application frontend

const API_URL = 'http://localhost:8080/api';
const API_URI = window.location.search;

// Fonctions Post pour les actions
const postFunctions = {
    // template
    async postAjouterPanier() {
        const url = API_URL + '/utilisateur/' + this.username + '/ajouterPanier';
        console.log(this.$refs.nbProduit.value);
        try {
            const response = await axios.post(url, {
                produitId: this.produit.id,
                nbProduit: this.$refs.nbProduit.value,
                commentaire: this.$refs.commentaire.value
            });
    
            // Logique spécifique à réaliser du côté client
            console.log("Réponse reçue :", response.data);
    
        } catch (error) {
            console.error("Erreur lors de l'ajout au panier :", error);
            // Gérer l'erreur (par exemple, afficher un message à l'utilisateur)
        }
    },
    async postnier() {
        // event.preventDefault(); // Empêche le comportement par défaut du formulaire
        console.log(this.username);
        // var url = API_URL + '/URL_du_post';
        // const username = document.getElementById('Id_du_formulaire').value;

        // try {
        //     const response = await axios.post(url, {
        //         Id_du_formulaire: Id_du_formulaire
        //     });

        //     //logique spécifique à réaliser du côté client

        //     //Redirection en cas de succès
        //     window.location.href = 'URL/REDIRECTION';
        // }
        // catch (error) {
        //     console.error('Erreur lors de la connexion :', error);
        //     // Gérer l'erreur (par exemple, afficher un message à l'utilisateur)
        // }
    },

    // post pour le LoginForm
    async postLogin() {
        // event.preventDefault();

        var url = API_URL + '/login';

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            const response = await axios.post(url, {
                username: username,
                password: password
            });

            //sauvegarde du token
            if (response.data.dto.authToken) {
                localStorage.setItem('authToken', response.data.dto.authToken);
            }

            window.location.href = '/produits/liste';
        } catch (error) {
            console.error('Erreur lors de la connexion :', error);
        }
    }
}


// Fonction principale qui envoi un get à la page actuel
function get_Vue_data(url, uri = API_URI) {
    var url = API_URL + url + uri;

    const app = Vue.createApp({
        data() {
            return {
                listProduits: [],
                produit: {
                    meta: {}
                },
                buyable: true,
                vendeurUsername: "",

                userid: "",
                userpp: "",
                username: "",

                loading: false,
                error: null
            };
        },
        async created() {
            this.loading = true;
            try {
                const response = await axios.get(url);

                this.buyable = response.data.dto.buyable;
                this.produit = response.data.dto.produit;
                this.listProduits = response.data.dto.produits;
                this.vendeurUsername = response.data.dto.vendeurUsername;

                this.userid = response.data.userid;
                this.userpp = response.data.userPP;
                this.username = response.data.username;
                console.log(response.data);
            } catch (err) {
                this.error = err.message;
                console.log(this.error);
            } finally {
                this.loading = false;
            }
        },
        components: {
            'header-component': HeaderComponent,
            'footer-component': FooterComponent
        },
        methods: postFunctions
        
    });
    app.mount('#app');
}

// Interceptor de requête pour ajouter le token
axios.interceptors.request.use(function (config) {
    const token = localStorage.getItem('authToken');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});
// Interceptor pour erreur
// c'est dégeulasse, normalement c'est l'erreur 401, ici pour toutes les erreurs le token
// est supprimé
axios.interceptors.response.use(response => response, error => {
    if (error.response && error.response.status === 500) {
        localStorage.removeItem('authToken');
        location.reload()
    }
    return Promise.reject(error);
});

//Header
const headerVue =
    `
<h1 class="header-title">
    <a href="/produits/liste">
        <span style="--i:0;">G</span>
        <span style="--i:1;">r</span>
        <span style="--i:2;">o</span>
        <span style="--i:3;">o</span>
        <span style="--i:4;">v</span>
        <span style="--i:5;">T</span>
        <span style="--i:6;">r</span>
        <span style="--i:7;">a</span>
        <span style="--i:8;">d</span>
        <span style="--i:9;">e</span>
    </a>
</h1>
<div v-if="userid === null">
        <a href="/login"><button type="button" class="btn btn-primary headtool">Connexion</button></a>
        <a href="/signup"><button type="button" class="btn btn-warn headtool">Inscription</button></a>
    </div>
    <div v-else>
        <a :href="'/utilisateur/'+username"><button class="btn btn-circle headtool">
                <img :src="'/images/'+userpp" alt="Image">
            </button></a>
    </div>
    <div class="card-body header-search">
    <div class="search-bar mt-3">
        <form method="get" action="/produits/liste" id="searchForm">
            <!-- <label>Keyword</label> -->
            <input class="form-control form-control-lg" type="search" placeholder="Recherche" name="keyword"
                aria-label="Keyword" id="input-keyword">
            <button class="btn btn-outline-secondary" type="button" id="advancedSearchToggle" aria-haspopup="true"
                aria-expanded="false">
                +
            </button>
            <div class="advanced-search mt-2" id="advancedSearch">
                <input class="form-control" type="search" placeholder="Artiste" name="artiste" aria-label="Artiste">
                <input class="form-control mt-2" type="search" placeholder="Titre" name="nom" aria-label="Titre">
                <!-- attention nom = titre-->
                <input class="form-control mt-2" type="search" placeholder="Album" name="album" aria-label="Album">
                <input class="form-control mt-2" type="search" placeholder="Genres" name="genres"
                    aria-label="Genres">
                <!-- TODO: chercher plusieurs genres-->
                <!-- années -->
                <div class="row mt-2">
                    <div class="col-md-6">
                        <input class="form-control" type="number" name="annee_inf" placeholder="Année Min.">
                    </div>
                    <div class="col-md-6">
                        <input class="form-control" type="number" name="annee_sup" placeholder="Année Max.">
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">🔎</button>
        </form>
    </div>
</div>

`;
const HeaderComponent = {
    props: ['userid', 'userpp', 'username'],
    template: headerVue
};
// Footer

const footerVue =
    `
<div class="container text-white pt-2"> 
        <div class="row">
            <!-- Section About -->  
            <div class="col-md-5">
                <h5 class="text-primary">À Propos</h5>
                <p style="text-align:justify">GroovTrade, l'application communautaire qui permet de vendre, d'acheter, et d'échanger des supports
                    musicaux neuf ou d'occasions entre entreprises, particuliers et passionés.</p>
            </div>

            <!-- Section Contact -->
            <div class="col-md-5">
                <h5 class="text-primary">Contact</h5>
                <p>DAUVET-DIAKHATE Haron</p>
            </div>

        </div>

        <!-- Droits d'auteur -->
        <div class="text-center py-3">
            <p>&copy; 2023 Dans le cadre de l'UE DEVREP. Sorbonne Université Sciences</p>
        </div>
    </div>
`;

const FooterComponent = {
    template: footerVue
};


