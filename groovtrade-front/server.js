const express = require('express');
const cors = require('cors');
const app = express();

app.use(express.static('./src'));
app.use(express.static('../src/main/resources/static'));

app.get('/', (req, res) => {
    res.sendFile(__dirname + '/src/index.html');
  });

app.get('/produits/liste', (req, res) => {
res.sendFile(__dirname + '/src/produit.html');
});

app.get('/produits/details/:id', (req, res) => {
res.sendFile(__dirname + '/src/detailsProduit.html');
});



app.get('/login', (req, res) => {
  res.sendFile(__dirname + '/src/login.html');
  });

const PORT = process.env.PORT || 8081;
app.listen(PORT, () => {
  console.log(`Serveur en écoute sur le port ${PORT}`);
});


app.use(cors());