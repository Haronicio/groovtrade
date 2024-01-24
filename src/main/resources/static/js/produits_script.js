// Featurs Flags with Flagsmith 
const FLAGSMITH_ENVID = "NWFYoCfJgPBZo9MPgFEmUy";

flagsmith.init({
    environmentID: FLAGSMITH_ENVID,
    onChange: (oldFlags, params) => {
        const { isFromServer } = params;

        // Theme
        if (flagsmith.hasFeature("dark_theme")) {
            document.documentElement.setAttribute("data-bs-theme", "dark");
        }
        //Discogs
        if (flagsmith.hasFeature("discog_completion")) {

            // Create a new button element
            var button = document.createElement("button");

            // Set attributes for the button

            button.id = "discogsBtn";               // Setting an ID
            button.className = "btn btn-primary";   // Setting classes

            // Create an image element
            var img = document.createElement("img");
            img.src = "https://www.discogs.com/service/header/public/assets/assets/logo-83221bf3.svg"; // Image source URL
            img.alt = "Discogs Logo"; // Alternative text
            img.width = "200";

            // Append the image to the button
            button.appendChild(img);

            // Append the button to the form-special container
            document.getElementById("form-special").appendChild(button);

            // Create a new input element
            var discogsTokenInput = document.createElement("input");

            // Set attributes for the input
            discogsTokenInput.type = "text";
            discogsTokenInput.className = "form-control";
            discogsTokenInput.id = "discogsToken";
            discogsTokenInput.placeholder = "Enter your Discogs token";

            // Append the input to a specific element in the DOM
            document.getElementById("form-special").appendChild(discogsTokenInput);

            // const DISCOGS_KEY = "";
            // const DISCOGS_SECRET = "";
            // const DISCOGS_TOKEN = "";

            document.getElementById('discogsBtn').addEventListener('click', function (event) {
                event.preventDefault();
               
                var nom = document.getElementById('nom').value;
                var album = document.getElementById('album').value;
                var annee = document.getElementById('annee').value;
                var artiste = document.getElementById('artiste').value;
                var token = document.getElementById('discogsToken').value;

                fetchDiscogsData(nom,album, annee, artiste,token);
            });

            function fetchDiscogsData(nom,album, annee, artiste,token) {

                if (!token) {
                    // Redirect to Discogs developers settings page
                    window.location.href = "https://www.discogs.com/fr/settings/developers";
                    return; // Exit the function
                }

                var query = `https://api.discogs.com/database/search?track=${nom}&release_title=${album}&year=${annee}&artist=${artiste}&per_page=1&token=${token}`;
                console.log(query);
                fetch(query)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                        return response.json();
                    })
                    .then(data => {
                        console.log(data);
                        updateFieldsAndOpenPopup(data)
                    })
                    .catch(error => {
                        console.error('There has been a problem with your fetch operation:', error);
                    });
            }
            function updateFieldsAndOpenPopup(data) {
                // Assuming data is the object containing your fetched results
                let release = data.results[0];
            
                // Update fields
                document.getElementById('nom').value = release.title;
                document.getElementById('annee').value = release.year;
            
                // Open popup
                let popupWindow = window.open("https://www.discogs.com/fr"+release.uri, "Popup", "width=600,height=1080");
                if (popupWindow) {
                    popupWindow.focus();
                } else {
                    alert('Popup blocked by browser');
                }
            }
        }
    }
});


function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
// Script pour qu les grilles soit clickable 
document.addEventListener('DOMContentLoaded', function () {
    const rows = document.querySelectorAll('.product-link');
    rows.forEach(row => {
        row.addEventListener('click', function () {
            // Vérifie si l'élément cliqué ou un de ses parents est un formulaire
            if (event.target.closest('.button-container')) {
                // Ne fait rien si un formulaire ou un élément de formulaire a été cliqué
                return;
            }
            const productId = this.dataset.id;
            window.location.href = `/produits/details/${productId}`;
        });
    });
});
// Gérer le modal càd message d'info

document.addEventListener('DOMContentLoaded', (event) => {
    const modalTrigger = document.getElementById('modalTrigger');
    if (modalTrigger) {
        const message = modalTrigger.getAttribute('data-message');
        const error = modalTrigger.getAttribute('data-error');
        console.log(error);
        if ((message && message !== 'null') || (error && error !== 'null')) {
            showModal(message ? 'message' : 'error', message || error);
        }
    }
});



function showModal(type, message) {
    // Sélectionner les éléments du modal
    var modalHeader = document.getElementById('modalHeader');
    var modalBody = document.getElementById('modalBody');

    // Définir la couleur de fond en fonction du type
    if (type === 'error') {
        modalHeader.style.backgroundColor = 'rgba(255, 0, 0, 0.5)'; // Rouge transparent
    } else {
        modalHeader.style.backgroundColor = 'rgba(0, 123, 255, 0.5)'; // Bleu transparent
    }

    // Définir le message
    modalBody.textContent = message;

    // Afficher le modal
    $('#messageModal').modal('show');
}

// Exemple d'utilisation
// showModal('error', 'Ceci est un message d\'erreur.');
// showModal('message', 'Ceci est un message informatif.');


//Script pour changer dynamiquement la quantité dans le panier
// document.addEventListener('DOMContentLoaded', function() {
//     var quantiteInput = document.getElementById('quantite');
//     if (quantiteInput) {
//         quantiteInput.addEventListener('change', function() {
//             var monFormulaire = document.getElementById('changeQ');
//             if (monFormulaire) {
//                 monFormulaire.submit();
//             }
//         });
//     }
// });


//Script pour gérer la taille des images
function validateImages() {
    const maxImgSize = 307200; // 300 Ko
    const maxImgNumber = 5;
    var files = document.getElementById('coverImages').files;

    if (files.length > maxImgNumber) {
        alert("5 images maximum");
        return false;
    }

    for (var i = 0; i < files.length; i++) {
        if (files[i].size > maxImgSize) {
            alert("L'image " + files[i].name + " est trop volumineuse.");
            return false;
        }
    }

    return true;
}


// Script pour gérer l'affichage des champs de recherche avancée
document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('advancedSearchToggle').addEventListener('click', function () {
        var advancedSearch = document.getElementById('advancedSearch');
        advancedSearch.style.display = advancedSearch.style.display === 'block' ? 'none' : 'block';
    });
});

// Script pour nettoyer l'URL si advanced search n'est pas utilisé
document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('searchForm').addEventListener('submit', function (event) {
        var advancedSearch = document.getElementById('advancedSearch');
        console.log(advancedSearch.style.display);
        if (advancedSearch.style.display === '') {                          //'none' = ''
            event.preventDefault();
            var keyword = document.getElementById('input-keyword').value;
            var url = '/produits/liste?';
            if (keyword) url += 'keyword=' + encodeURIComponent(keyword);
            console.log(url);
            window.location.href = url;
            sleep(2000);
        }
    });
});

function filtrerGenres() {
    let input = document.getElementById('genreSearch');
    let filter = input.value.toUpperCase();
    let div = document.getElementById('genresList');
    let spans = div.getElementsByTagName('span');

    for (let i = 0; i < spans.length; i++) {
        let label = spans[i].getElementsByTagName('label')[0];
        if (label.textContent.toUpperCase().indexOf(filter) > -1) {
            spans[i].style.display = "";
        } else {
            spans[i].style.display = "none";
        }
    }
}

// Coche les genres présent dans la chaine de charactère 
function checkGenres(genresString) {
    var genresArray = genresString.split(',');

    var checkboxes = document.querySelectorAll('input[name="genres"]');

    // Parcourir toutes les cases à cocher
    checkboxes.forEach(function (checkbox) {
        // Si le genre de la case à cocher est dans genresArray, cocher la case
        if (genresArray.includes(checkbox.value)) {
            checkbox.checked = true;
        }
    });
}
