# Développement d’une application (E-Shop ) Web avec Spring
DAUVET-DIAKHATE Haron et ZHANG Changrui

## Introduction

Dans le cadre de l'UE DevRep, nous réalisons un projet qui consiste à développer une application web simplifiée d'un système de type E-shop. Cette application doit inclure un catalogue, une recherche dans celui-ci, créer un compte utilisateur, ajouter au panier des articles, passer des commandes et consulter l'historique des commandes passés.
Pour cela nous utiliseront Spring Boot comme FrameWork, une base de donnée SQL pour maintenir les entités, une vue thymleaf pour un rendu côté serveur et une vue ReactJS pour le rendu côté client en association avec notre application RESTFUL.

Dans ce document nous verrons comment nous avons mis en oeuvre cette application, d'abord nous discuterons de nos idées et nos choix d'outils pour la conception, ensuite nous verrons comment nous avons structuré nos entités et nos assosciations dans la base de donnée, de qu'elle manière nous avons organiser nos logiques et nous finirons sur comment l'utilisateur intéragit avec notre application.

## Structure

> Plongez dans l'univers de GroovTrade pour enrichir vos collections ou partager vos trésors musicaux
                        ! Que vous soyez un mélomane passionné ou une entreprise musicale, découvrez l'opportunité
                        d'acheter et de vendre des vinyles, K7 et CD, neufs ou d'occasions. Des dernières sorties aux pépites indépendantes,
                        vos produits trouveront toujours leur place dans la collection d'un autre passionné.
                        Alors rejoignez-nous pour créer des connexions musicales uniques, élargir vos horizons sonores
                        et faire résonner votre passion au sein de la communauté GroovTrade.
                        
Cette description est celle de notre site de E-Shop, en effet nous avons choisi de réaliser un site d'achat et vente de seconde main de supports musicaux.
Dans cette section nous allons décire les différentes structures de données de notre site que ce soit au sein du JPA ou dans la base de donnée
Pour maintenir notre base de donnée nous avons créer une database **groovtrade**, il existe une copie de celle-ci dans le dossier *bd*. Vous pourrez l'importé en faisant `mysql -u username -p database_name < groovtrade.sql` dans votre console.

pour l'éssayer, connectez vous sur le compte de **myadmin** avec le mot de passe **admin** ou créer votre propre compte (attention l'image doit faire moin de 300Ko), retrouvé le tutoriel complet dans ma dans la partie *Guide d'utilisation* de ce document.

### Entités

Une Base de donnée relationnel ne peut avoir une structure similaire a celle des classes JAVA, en effet une base de donnée à des tables d'associations, tandis que les classes peuvent s'encapsuler. La base de donnée est donc plus rigide que les classes.
Dans notre système, multiplier les associations entre objets, c'est multiplier les tables et donc la complexité.

Pour palier a ce problème nous avons distingué les objets embarquable des objets entités.

![groovtradeUML](https://hackmd.io/_uploads/rJ06d-bUp.png)
> Le diagramme UML des relations entre les classes de l'application, les méthodes sont omises

* Utilisateur représente les utilisateurs qui ont un compte sur le site, ils ont une liste d'historique de commande, l'utilisateur à un Panier, cette classe est représenté comme une entité dans la base de donnée
* Un Historique représente une commande passé, elle peut être en cours, payé ou annulé, l'historique à une liste de PanierItem et une référence sur un utilisateur, cette classe est représenté comme une entité dans la base de donnée
* La classe Produit représente les produits mis en ligne par les Utilisateurs, un produit à une liste de ProduitImg, un ProduitType ainsi qu'un ProduitMeta, cette classe est représenté comme une entité dans la base de donnée

On se heurte à un problème dans la lecture de la base de donnée lors de la sérialisation, en effet Utilisateur référence des Historiques et un Historique référence un Utilisateur. Pour éviter ce problème gênant, on peut ajouter les annotation `@JsonBackReference` et `@JsonManagedReference`.

Les objets marqué `@Embeddable` sont des objets qui n'on pas besoin de table dans la base de donnée, tout leurs attributs sont maintenu dans la table d'assosciation des entités qui les encapsulent. Ces classes comprennent :

* Panier qui représente le panier d'un utilisateur, il ne peut éxister sans Utilisateur, mais identifié un panier par un id n'aurait pas de sens car c'est celui de l'Utilisateur
* PanierItem est une sorte d'abstraction dans l'application, en effet il faut un moyen de maintenir un nombre d'article par produit dans le Panier de l'Utilisateur et dans les historiques de l'Utilisateur, il référence donc un produit. De la même manière que le Panier, maintenir un id pour chaque item  dans les différents historiques et paniers des utilisateurs compliquerai la base de donnée
* ProduitMeta ProduitType ProduitImg représente toutes les données périphérique d'un produit, ces informations, ils sont unique à chaque Produit

Pour encapsuler une classe embarquable on annote l'attribut de `@Embedded`, s'il a une association multiple on l'annote de `@ElementCollection` et on renseigne le nom de la table d'assosciation et par qu'elle joint on peut l'identifier.


### Base de Donnée

![groovtradeBD](https://hackmd.io/_uploads/r1RPaWZIa.png)
> Tables de la base de donnée groovtrade

On voit ici par exemple que au vu de la relation entre Produit et ProduitImg, ProduitMeta, ProduitType ; sont encapsulé dans la table Produit. Tandis qu'il y a 2 tables pour représenter les listes d'items dans le panier (assoscié avec l'id de l'utilisateur car c'est une entité qui permet d'identifié les paniers), les listes d'items dans les historiques.


## Logiques

Pour avoir un aperçu de l'organisation des logiques, voici l'organisation des packages et dossier de l'application.

```
.
├── HELP.md
├── bd
│   └── groovtrade.sql
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── fr
│   │   │       └── haron
│   │   │           └── groovtrade
│   │   │               ├── GroovtradeApplication.java
│   │   │               ├── configs
│   │   │               │   └── CustomWebConfig.java
│   │   │               ├── controllers
│   │   │               │   ├── APIRestController.java
│   │   │               │   ├── GlobalControllerAdvice.java
│   │   │               │   ├── LoginController.java
│   │   │               │   ├── ProduitController.java
│   │   │               │   ├── SignupController.java
│   │   │               │   └── UtilisateurController.java
│   │   │               ├── dao
│   │   │               │   ├── HistoriqueRepository.java
│   │   │               │   ├── ProduitRepository.java
│   │   │               │   └── UtilisateurRepository.java
│   │   │               ├── dto
│   │   │               │   ├── DTOInterface.java
│   │   │               │   ├── ErrorDTO.java
│   │   │               │   ├── GlobalDTO.java
│   │   │               │   ├── MessageDTO.java
│   │   │               │   ├── RedirectDTO.java
│   │   │               │   ├── produit
│   │   │               │   │   ├── AjouterProduitDTO.java
│   │   │               │   │   ├── ModifierProduitDTO.java
│   │   │               │   │   ├── ProduitDetailsDTO.java
│   │   │               │   │   └── ProduitListeDTO.java
│   │   │               │   └── utilisateur
│   │   │               │       ├── AjouterPanierDTO.java
│   │   │               │       ├── CheckoutDTO.java
│   │   │               │       ├── HistoriqueAchatsDTO.java
│   │   │               │       ├── ModifierPanierDTO.java
│   │   │               │       ├── ProfileDTO.java
│   │   │               │       └── UtilisateurDTO.java
│   │   │               ├── entities
│   │   │               │   ├── Historique.java
│   │   │               │   ├── Panier.java
│   │   │               │   ├── PanierItem.java
│   │   │               │   ├── Produit.java
│   │   │               │   ├── ProduitImg.java
│   │   │               │   ├── ProduitMeta.java
│   │   │               │   ├── ProduitSong.java
│   │   │               │   ├── ProduitType.java
│   │   │               │   └── Utilisateur.java
│   │   │               └── security
│   │   │                   ├── CustomUserDetailsService.java
│   │   │                   ├── RefererValidator.java
│   │   │                   └── SecurityConfig.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── static
│   │       │   ├── css
│   │       │   │   └── produits_style.css
│   │       │   ├── font
│   │       │   │   ├── aAbstractGroovy.otf
│   │       │   │   └── aAbstractGroovy.ttf
│   │       │   ├── images
│   │       │   │   ├── ...
│   │       │   └── js
│   │       │       └── produits_script.js
│   │       └── templates
│   │           ├── ajouterProduit.html
│   │           ├── checkout.html
│   │           ├── detailsProduit.html
│   │           ├── footer.html
│   │           ├── guide.html
│   │           ├── header.html
│   │           ├── historique.html
│   │           ├── index.html
│   │           ├── modifierProduit.html
│   │           ├── msg.html
│   │           ├── produits.html
│   │           ├── profile.html
│   │           ├── signup.html
│   │           └── utilisateur.html
│   └── test
│       └── java
│           └── fr
│               └── haron
│                   └── groovtrade
│                       └── GroovtradeApplicationTests.java
```
> l'arbre de la hiérachie de notre application



### Controllers

Les **controllers** permettent de mapper les redirections de l'application, ils permettent ainsi de créeer un arbre de lien pour l'utilisateur :

Dans cette section nous nous interesserons au mapping des requêtes autour de l'application rendu côté server (nous reviendrons plus tard sur la vue généré côté server et côté client)

Les controllers (hors RESTFul) échangent avec la vue des `Model`, qui encapsulent des attributs au quel la vue peut accéder, les controllers attrape les requêtes HTTP Post ou Get pour effectuer des logiques. L'utilisateur via la vue , peut donc intéragir avec des  logiques, celle-ci constituent le fonctionnement de notre application :

* GlobalAdviceController
    Ce controller permet de délivrer un `Model` à la vue quelque soit la vue à générer et attraper les requêtes quelque soit leurs provenance, on s'en sert nottament pour le mapping des erreurs et pour transmettre certaines information tel que le nom de l'utilisateur ou son image de profile.
    
* `LoginController`
    Mappe les logiques a effectué à partir de la racine */* de l'application, notament ou aller après un login réussi

* `SignupController`
    Gère le mapping pour l'inscription d'un nouvel utilisateur, il renvoi nottament le formulaire pour que l'utilisateur s'inscrive, et capture les requêtes HTTP a destination de l'inscription

* `ProduitController`
    L'un des deux Controller maintenant les logiques cruciales de l'application,celui-ci est le point d'entrée du site */produits/liste* qui représente le catalogue des produits et toutes les logiques pour représenter et gérer celui-ci:
    * */produits/liste Get* affiche le catalogue
    * */produits/ajouterProduit Get* renvoi la vue pour le formulaire d'ajout de produit
    * */produits/ajouter Post* ajoute un produit au catalogue
    * */produits/delete Get* supprime un produit du catalogue, il n'est jamais utiliser car on ne retire pas un produit mais on l'archive
    * */produits/details/{id} Get* accède aux détails d'un produit par son identifiant
    * */produits/modifierProduit Get* renvoi la vue pour le formulaire de modification d'un produit
    * */produit/modifier Post* modifie les informations d'un produit
    
    
* *UtilisateurController*
     il permet de mapper toutes les logiques en rapport avec l'utilisateur, notamment ajouter au panier, le checkout, les historiques, sa liste de vente.

### Repositorys

Les **repositorys** représente la base de donné dans la mémoire interne

### Sécurités

L'application est doté de sécurité simple pour authentifié 

### API REST

L'API REST permet de donné une API à l'utilisateur pour utiliser les logiques de notre application , il peur par exemple créé sa propre vue pour notre application.

Ce base sur un système de Data Transfer Object pour gérer la sérialisation des données
Chaque DTO implémente `DTOInterface`, il existe un `GlobalDTO` car avec l'API REST on ne peut pas communiqué un `Model` à la vue mais que des données sérialisées, GlobalDTO est le DTO renvoyé dans chaque requête HTTP depuis le serveur, celui-ci encapsule les DTO pour chaque mapping. Il y a des DTO spécieux comme `MessageDTO` `ErrorDTO` et `RedirectDTO` pour suggérer à l'utilisateur de l'API d'actionner des mécanisme spéciaux.

Le Controller APIREstController reproduit toutes les logiques vues précedemment et les offres avec l'URL */api/...*

l'API peux s'utiliser conjointement avec ReactJS par exemple 

## Vues

Il ya deux paradigmes dans les applications WEB : le serveur génère la vue, ou le client génère la vue.

dans tous les cas elle se base sur la réutilisabilité

### Thymleaf

Vues généré par le serveur, avec l'utilisation du framework thymleaf au sein du code HTML.

Le site généré par le serveur est complètement fonctionnel, accédez au catalogue en accédant à `/produits/liste`. Pour la réutilisabilité, par exemple, la vue utilisateur est partagé avec le mapping de `/utilisateur/{username}/ventes` `/utilisateur/{username}/panier` `/utilisateur/{username}/historique/{id}/details` pour afficher une liste de PanierItems.

#### Guide d'utilisation

Démarré le Server pour rendre disponible la vue Thymleaf générer par le serveur, allé à l'adresse */welcome* pour afficher le guide d'utilisation du site.

Voici quelques capture d'écran

![Capture d’écran 2023-12-09 010344 (Personnalisé)](https://hackmd.io/_uploads/S1mkeNZL6.png)
![Capture d’écran 2023-12-09 010509](https://hackmd.io/_uploads/B1I91EZ86.png)
![Capture d’écran 2023-12-09 010531](https://hackmd.io/_uploads/ryv9yN-86.png)
![Capture d’écran 2023-12-09 010603](https://hackmd.io/_uploads/H1D91EWLT.png)
![Capture d’écran 2023-12-09 010618](https://hackmd.io/_uploads/Byw9JN-I6.png)



### ReactJS

l'API peux s'utiliser conjointement avec ReactJS par exemple pour généré du code HTML dans le quel est inclu nos objets sérialisés.

Dans l'état actuelle de l'application, ReactJS n'est pas complètement fonctionnelle, en revanche l'API REST fonctionne. A l'avenir, nous transformeront tous les blocs thymleaf en fonction ReactJS.

Nous avons enfaite développer ReactJS avec une API de test sur une branche et les vues thymleaf avec les controllers dont l'API final sur une autre, nous n'avons pas eu le temps encore d'adapter les deux branches.

Vous retrouverez cettevue dans le dossier */front-end*


## Conclusion

Prochainement nous comptons ajouter des micro services éventuellement pour avoir un forum dans lequel les utilsateur peuvent chater entre eux et les aider a déniché des articles rares, la possibillité d'ajouté des extraits audio, un lecteur appelé "radio" pour explorer des genres, l'intégration de l'application dans un Docker pour faciliter la portabilité
