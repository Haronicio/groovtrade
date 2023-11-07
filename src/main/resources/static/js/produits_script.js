document.addEventListener('DOMContentLoaded', function() {
    const rows = document.querySelectorAll('.clickable-row');
    rows.forEach(row => {
        row.addEventListener('click', function() {
            const productId = this.dataset.id;
            window.location.href = `/produits/details/${productId}`;
        });
    });
});

function filterGenres() {
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
