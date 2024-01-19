// theme
document.documentElement.setAttribute("data-bs-theme", "dark");


function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }
// Script pour qu les grilles soit clickable 
document.addEventListener('DOMContentLoaded', function() {
    const rows = document.querySelectorAll('.product-link');
    rows.forEach(row => {
        row.addEventListener('click', function() {
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
document.addEventListener('DOMContentLoaded', function() {
document.getElementById('advancedSearchToggle').addEventListener('click', function() {
    var advancedSearch = document.getElementById('advancedSearch');
    advancedSearch.style.display = advancedSearch.style.display === 'block' ? 'none' : 'block';
});
});

// Script pour nettoyer l'URL si advanced search n'est pas utilisé
document.addEventListener('DOMContentLoaded', function() {
document.getElementById('searchForm').addEventListener('submit', function(event) {
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
    checkboxes.forEach(function(checkbox) {
        // Si le genre de la case à cocher est dans genresArray, cocher la case
        if (genresArray.includes(checkbox.value)) {
            checkbox.checked = true;
        }
    });
}
