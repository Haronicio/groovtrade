document.addEventListener('DOMContentLoaded', function() {
    const rows = document.querySelectorAll('.clickable-row');
    rows.forEach(row => {
        row.addEventListener('click', function() {
            const productId = this.dataset.id;
            window.location.href = `/produits/details/${productId}`;
        });
    });
});