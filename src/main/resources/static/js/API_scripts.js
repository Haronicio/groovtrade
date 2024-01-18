// Ce script contient les datas et les logiques pour VueJS

const API_URL = 'http://localhost:8080/api';
const API_URI = window.location.search;


// Page principal
function get_Vue_data(url,uri=API_URI){
    var url = API_URL + url + uri;

        const app = Vue.createApp({
            data() {
                return {
                    listProduits: [],
                    userid: "",
                    userPP: "",
                    username: "",
                    loading: false,
                    error: null
                };
            },
            async created() {
                this.loading = true;
                try {
                    const response = await axios.get(url);
                    this.listProduits = response.data.dto.produits;
                    this.userid = response.data.userid;
                    this.userPP = response.data.userPP;
                    this.username = response.data.username;
                    //console.log(this.listProduits.dto.produits);
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
            }
        });
        app.mount('#app');
}

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
                <img :src="'/images/'+userPP" alt="Image">
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
    props: ['userid','userPP','username'],
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
                <p>DAUVET-DIAKHATE Haron et ZHANG Changrui</p>
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


