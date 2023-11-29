function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }
// Script pour qu les grilles soit clickable 
document.addEventListener('DOMContentLoaded', function() {
    const rows = document.querySelectorAll('.clickable-row');
    rows.forEach(row => {
        row.addEventListener('click', function() {
            const productId = this.dataset.id;
            window.location.href = `/produits/details/${productId}`;
        });
    });
});

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
