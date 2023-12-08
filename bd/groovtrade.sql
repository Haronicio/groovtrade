-- MySQL dump 10.13  Distrib 8.0.35, for Linux (x86_64)
--
-- Host: localhost    Database: groovtrade
-- ------------------------------------------------------
-- Server version	8.0.35-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (45);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historique`
--

DROP TABLE IF EXISTS `historique`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historique` (
  `historique_id` bigint NOT NULL AUTO_INCREMENT,
  `archived` bit(1) NOT NULL,
  `date` varchar(255) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL,
  `utilisateur_id` bigint DEFAULT NULL,
  `livraison` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`historique_id`),
  KEY `FKapis6adjm795lp61gp45lktpq` (`utilisateur_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historique`
--

LOCK TABLES `historique` WRITE;
/*!40000 ALTER TABLE `historique` DISABLE KEYS */;
INSERT INTO `historique` VALUES (10,_binary '','2023-12-08T06:38:05.423579500','process',2,'ert zert 51 fr'),(11,_binary '','2023-12-08T07:10:05.099528400','process',2,'a a hg fr'),(12,_binary '','2023-12-08T07:22:35.437009','canceled',2,'edf sdef sedfz fr');
/*!40000 ALTER TABLE `historique` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historique_panier_items`
--

DROP TABLE IF EXISTS `historique_panier_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historique_panier_items` (
  `historique_historique_id` bigint NOT NULL,
  `commentaire` varchar(255) DEFAULT NULL,
  `produit_id` bigint DEFAULT NULL,
  `quantite` int NOT NULL,
  KEY `FK7bwyf79vv11lulo8q7vcc7tqq` (`produit_id`),
  KEY `FKt0iqlhsfw39m36ohx49bcqhrt` (`historique_historique_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historique_panier_items`
--

LOCK TABLES `historique_panier_items` WRITE;
/*!40000 ALTER TABLE `historique_panier_items` DISABLE KEYS */;
INSERT INTO `historique_panier_items` VALUES (2,'',20,1),(2,'',38,3),(3,'',36,1),(4,'',24,1),(4,'',15,1),(4,'',34,1),(5,'',40,10),(6,'',39,2),(7,'',36,2),(8,'',16,1),(8,'',14,1),(9,'',20,1),(10,'',16,1),(10,'',20,1),(11,'',20,1),(12,'',18,1);
/*!40000 ALTER TABLE `historique_panier_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `img_produit`
--

DROP TABLE IF EXISTS `img_produit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `img_produit` (
  `produit_id` bigint NOT NULL,
  `path` varchar(255) DEFAULT NULL,
  KEY `FKoeipk998e1vmrnemjxctujstm` (`produit_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `img_produit`
--

LOCK TABLES `img_produit` WRITE;
/*!40000 ALTER TABLE `img_produit` DISABLE KEYS */;
INSERT INTO `img_produit` VALUES (14,'1.png'),(15,'Abraxas.jpg'),(16,'Pants Shitters.jpg'),(20,'Marginalia.jpg'),(18,'Debut.jpg'),(21,'Absolve.jpg'),(24,'Night Sketches.jpg'),(25,'... I Care Because You Do.jpg'),(26,'Intercepted Message.jpg'),(27,'0.9.jpg'),(28,'On & On US CD single.jpg'),(33,'Baduizm.jpg'),(34,'tert.jpg'),(36,'Single.jpg'),(38,'yesy.jpg'),(39,'DDS.jpg'),(14,'11.jpg'),(39,'DDSDDS.jpg'),(39,'DDSDDS.jpg'),(39,'DDSDDS.jpg'),(34,'10portrait-650-1.jpg.jpg'),(34,'11geneve-carre.jpg.jpg'),(34,'12geneve-lune.jpg.jpg'),(40,'24DALL·E 2023-12-01 01.24.42jpg'),(40,'21geneve-carre.jpg.jpg'),(40,'22geneve-lune.jpg.jpg'),(40,'23AlbumCovers_Blonde-1200x1200.jpg.jpg'),(40,'20portrait-650-1.jpg.jpg'),(40,'25sddefault.jpg.jpg'),(40,'20geneve-carre.jpg.jpg'),(40,'21geneve-lune.jpg.jpg'),(40,'22DALL·E 2023-12-01 01.24.42.jpg'),(41,'80Capture d’.png'),(42,'20abricot.jpg'),(43,'801152063871.avif'),(44,'20portrait-6.jpg');
/*!40000 ALTER TABLE `img_produit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `panier_produits`
--

DROP TABLE IF EXISTS `panier_produits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `panier_produits` (
  `utilisateur_id` bigint NOT NULL,
  `produit_id` bigint DEFAULT NULL,
  `quantite` int NOT NULL,
  `commentaire` varchar(255) DEFAULT NULL,
  KEY `FKhxhvd9enptyrccoocyyey4d26` (`produit_id`),
  KEY `FKtepaxl1a2l4ksheavp48hicln` (`utilisateur_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `panier_produits`
--

LOCK TABLES `panier_produits` WRITE;
/*!40000 ALTER TABLE `panier_produits` DISABLE KEYS */;
INSERT INTO `panier_produits` VALUES (2,15,1,'');
/*!40000 ALTER TABLE `panier_produits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produit_songs`
--

DROP TABLE IF EXISTS `produit_songs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produit_songs` (
  `id` bigint NOT NULL,
  `path` varchar(255) DEFAULT NULL,
  `produit_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmkdjoo08o8p8uqu8s2uu6dljv` (`produit_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produit_songs`
--

LOCK TABLES `produit_songs` WRITE;
/*!40000 ALTER TABLE `produit_songs` DISABLE KEYS */;
/*!40000 ALTER TABLE `produit_songs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produits`
--

DROP TABLE IF EXISTS `produits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produits` (
  `id` bigint NOT NULL,
  `description` varchar(3000) DEFAULT NULL,
  `prix` double NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `album` varchar(500) DEFAULT NULL,
  `annee` int NOT NULL,
  `artiste` varchar(500) DEFAULT NULL,
  `genres` varchar(3000) DEFAULT NULL,
  `nom` varchar(500) DEFAULT NULL,
  `utilisateur_id` bigint DEFAULT NULL,
  `nb_produit` int NOT NULL,
  `archived` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produits`
--

LOCK TABLES `produits` WRITE;
/*!40000 ALTER TABLE `produits` DISABLE KEYS */;
INSERT INTO `produits` VALUES (15,'Santana Abraxas, acquit en 1980, Bonne état mais la piste 2 saute de temp en temp',25,'VINYLE33','Abraxas',1970,'Santana','ROCK,FOLK','Santana Abraxas (full Album)',1,10,_binary '\0'),(14,'Cette album est secret',314,'CD','1',1,'1','JAZZ','Secret',1,9,_binary ''),(16,'Huit mois après leur premier EP, le supergroupe electronica TASTE revient avec leur successeur intitulé Pants Shitters.',25,'VINYLE33','Pants Shitters',2023,'Taste','ROCK,ELECTRO_SWING','Le nouvel album du groupe Taste déjà dispo en vinyle ! ',1,8,_binary '\0'),(20,'Includ Marginalia\'s songs from 1 to 40',60,'VINYLE33','Marginalia',2018,'Masakatsu Takagi','CONTEMPORARY_JAZZ','Marginalia by the japanase Masakatsu Takagi 高木 正勝',1,7,_binary '\0'),(18,'Ci kow juroom benneel gaal gi Bjork defoon ci atum 1993',5,'CD','Debut',1993,'Bjork','EXPERIMENTAL,VOCAL_TRANCE','Album gu nekk ci yoon wi !',1,9,_binary '\0'),(21,'Unique K7 du nouveau single de Jacques, une véritable pépite !',2001,'K7','Absolve',2023,'Jacques','EXPERIMENTAL,ELECTRO_SWING','Absolve by Jacques (Single version)',1,11,_binary '\0'),(24,'Tracklist :  1. You & I 2. Theatrical State Of Mind 3. About Felix 4. Bubbles 5. Pacific Telephone 6. Good For Nothing and more !',10,'CD','Night Sketches',2019,'Papooz','POP,ELECTRONIC,DANCE','Night Sketches - Papooz (french pop)',1,10,_binary '\0'),(25,'Parfait état. Assez difficile à trouver en France. ',77,'VINYLE33','... I Care Because You Do',1995,'Aphex Twin','ELECTRONIC,EXPERIMENTAL,AVANT_GARDE,ALTERNATIVE','Album published in 1995 by Aphex Twin',1,1,_binary '\0'),(26,'Caractéristiques :  Vinyle Support, 33 T 30 CM Poids, 380 g, Largeur 313 mm, épaisseur 8 mm, Hauteur 313 mm ',34,'VINYLE33','Intercepted Message',2023,'Osees','UK_GARAGE','Very new album aviable now ',1,1,_binary '\0'),(27,'Single version du titre 0.9 issu de l\'album éponyme du DUC',92,'CD','0.9',2008,'Booba','HIP_HOP_RAP,GANGSTA_RAP','0.9 Booba (Single)',1,1,_binary '\0'),(28,'On & On  (album version) 3:45 \r\nBaduizm snippets (\"Otherside of the Game\"/\"Next Lifetime\"/\"Sometimes...\"/\"4 Leaf Clover\") 5:08 \r\nby Erykah Badu \r\nSingle \r\nCD\r\n\r\nSome scratch on cover',5,'CD','On & On US CD single',1996,'Erykah Badu','RNB_SOUL,NEO_SOUL','On & On US CD single',1,1,_binary '\0'),(33,'Baduizm état catastrophique ☠ je l\'ai détruit dans le lecteur cd de mon auto en mettant un cd de Mpokora\r\n\r\n1.	\"Rimshot (Intro)\"	Erykah Badu, Madukwu Chinwah	Chinwah	1:56\r\n2.	\"On & On\"	Badu, Jahmal \"JaBorn\" Cantero	Bob Power, JaBorn	3:45\r\n3.	\"Appletree\"	Badu, Robert Bradford	Ike Lee III, Badu	4:25\r\n4.	\"Otherside of the Game\"	Badu, Questlove, Richard Nichols, James Poyser	The Roots, Nichols	6:33\r\n5.	\"Sometimes (Mix #9)\"	Badu, The Roots, Nichols, Poyser	The Roots, Nichols, Poyser	0:44\r\n6.	\"Next Lifetime\"	Badu, Anthony Scott	Tone the Backbone	6:26\r\n7.	\"Afro (Freestyle Skit)\"	Badu, Poyser, Jafar Barron	Badu, Poyser, Barron	2:04\r\n8.	\"Certainly\"	Badu, Chinwah	Chinwah	4:43\r\n9.	\"4 Leaf Clover\"	David Lewis, Wayne Lewis	Ike Lee III, Badu	4:34\r\n10.	\"No Love\"	Badu, Bradford	Bradford	5:08\r\n11.	\"Drama\"	Badu, Tyallen Macklin	Bob Power	6:02\r\n12.	\"Sometimes...\"	Badu, The Roots, Nichols, Poyser	The Roots, Nichols, Poyser	4:10\r\n13.	\"Certainly (Flipped It)\"	Badu, Chinwah	Chinwah	5:26\r\n14.	\"Rimshot (Outro)\"	Badu, Chinwah	Chinwah	2:19\r\nTotal length:	58:151.	\"Rimshot (Intro)\"	Erykah Badu, Madukwu Chinwah	Chinwah	1:56\r\n2.	\"On & On\"	Badu, Jahmal \"JaBorn\" Cantero	Bob Power, JaBorn	3:45\r\n3.	\"Appletree\"	Badu, Robert Bradford	Ike Lee III, Badu	4:25\r\n4.	\"Otherside of the Game\"	Badu, Questlove, Richard Nichols, James Poyser	The Roots, Nichols	6:33\r\n5.	\"Sometimes (Mix #9)\"	Badu, The Roots, Nichols, Poyser	The Roots, Nichols, Poyser	0:44\r\n6.	\"Next Lifetime\"	Badu, Anthony Scott	Tone the Backbone	6:26\r\n7.	\"Afro (Freestyle Skit)\"	Badu, Poyser, Jafar Barron	Badu, Poyser, Barron	2:04\r\n8.	\"Certainly\"	Badu, Chinwah	Chinwah	4:43\r\n9.	\"4 Leaf Clover\"	David Lewis, Wayne Lewis	Ike Lee III, Badu	4:34\r\n10.	\"No Love\"	Badu, Bradford	Bradford	5:08\r\n11.	\"Drama\"	Badu, Tyallen Macklin	Bob Power	6:02\r\n12.	\"Sometimes...\"	Badu, The Roots, Nichols, Poyser	The Roots, Nichols, Poyser	4:10\r\n13.	\"Certainly (Flipped It)\"	Badu, Chinwah	Chinwah	5:26\r\n14.	\"Rimshot (Outro)\"	Badu, Chinwah	Chinwah	2:19\r\nTotal length:	58:15',1,'CD','Baduizm',1997,'Erykah Badu','RNB_SOUL,NEO_SOUL','Baduizm album (complètement cassé)',1,1,_binary '\0'),(34,'sdfg',23,'CD','tert',12,'rehtjy','JAZZ','rt',1,10,_binary '\0'),(36,'Selection de cd single rap oldschool classique francais neuf 10€ l\'unité\r\n\r\n1. Idéal J - Hardcore\r\n2. Alibi Montana - 1260 jours\r\n3. Fabe - L\'impertinent\r\n4. 113 - Truc de fou\r\n5. Faf Larage - C\'est ma cause\r\n6. La Cliqua - Les quartiers chauffent\r\n7. Les Sages Poètes de la Rue feat. Don Choa - Pour qui ? Pourquoi ?\r\n8. Rohff ft. Natty - Le son qui tue\r\n9. One Shot - Millenaire\r\n10. Booba - Numéro 10\r\n11. Nèg Marrons feat. Mystik, Pit Baccardi, Rohff - On fait les choses\r\n12. Fonky Family, 3ème Œil, Faf Larage, K-Rhyme Le Roi, Freeman & Akhénaton - Le retour du Shit Squad\r\n13. Rohff feat. Wallen - Charisme\r\n14. Ärsenik & Passi - Par où t\'es rentré ?\r\n15. Sniper - Gravé dans la roche\r\n16. Freeman feat. K-Rhyme Le Roi - L\'Palais De Justice\r\n17. Ärsenik - Boxe avec les mots\r\n18. Psy 4 de la Rime - Au taquet\r\n19. Fabe ft. East - Mots Vrais\r\n20. Sniper - Trait pour Trait\r\n21. Lord Kossity - Morenas\r\n22. Demon One feat. Soprano - J\'étais comme eux\r\n23. Mafia K\'1 Fry feat. Fonky Family - C\'est Torride\r\n24. 113 & Rohff - Le Chant Du Vice\r\n25. Boss All Stars - Freestyle\r\n26. Cut Killer - Sound Of Da Police',10,'CD','Single',2000,' Idéal J Alibi Montana Fabe 113 Faf Larage La Cliqua Les Sages Poètes de la Rue Don Choa Rohff Natty One Shot Booba Nèg Marrons Mystik Pit Baccardi Fonky Family 3ème œil K-Rhyme Le Roi Freeman Akhénaton Wallen Ärsenik Passi Sniper K-Rhyme Le Roi Psy 4 de la Rime East Lord Kossity Demon One Soprano Mafia K\'1 Fry Boss All Stars Cut Killer','HIP_HOP_RAP,TRAP_MUSIC,GANGSTA_RAP','Selection de cd rap oldschool francais neuf 10€ l\'unité',2,21,_binary '\0'),(38,'test',1,'CD','yesy',78,'TYUIO','CHIPTUNE','test',1,10,_binary '\0'),(39,'rtyu',22,'CD','DDS',23,'HJK','SYMPHONIC','AZ',1,0,_binary ''),(40,'testtests',5,'CD','test',8,'ghj','REGGAE','testetstA',2,0,_binary ''),(42,'qsfd',52,'CD','dfh',53,'sdgx','MERENGUE,TRAP','ghf',2,1,_binary ''),(41,'Album secret sorti en 1 seul exemplaire ',667,'CD','Nibiru',2020,'Osirus Jack','HIP_HOP_RAP','Album secret ',8,1,_binary '\0'),(43,'L\'album de pomme',15,'CD','Consolation',2022,'Pomme','POP,ELECTRONIC,CHAMBER,NEO_SOUL','ALBUM DE POMME TROP COOL - consolation',8,1,_binary '\0'),(44,'test final',7,'CD','mort',666,'sdf','TRANCE,HARDCORE_PUNK,HARDCORE_TECHNO,HARDCORE_BREAKS,HARDCORE','test de la mort',2,1,_binary '\0');
/*!40000 ALTER TABLE `produits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utilisateur` (
  `userid` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `img_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilisateur`
--

LOCK TABLES `utilisateur` WRITE;
/*!40000 ALTER TABLE `utilisateur` DISABLE KEYS */;
INSERT INTO `utilisateur` VALUES (1,'c@gmail.com','$2a$10$yzn2KrxOitfMXBWznlqKd.GZ5JDrwk88JLaGYkg5cZMtwAqvZPOD6','ADMIN','myadmin','Pants Shitters.jpg'),(2,'haron@gmail.com','$2a$10$oUHvbLZHHCNs/quyiHVt6.Eiyv/HBhWC4RiKNg7A13sr1zOw.LhVG','ADMIN','haron','0.9.jpg'),(8,'johanned@outlook.fr','$2a$10$SzJe6S2HB/UX37tT/3VWw.ixNMr9EuO89IVJ0CNtOK4.xpVY75KgC','USER','Joelastarrr','Joelastarrr.profile.download20.png'),(7,'loulou@aol.fr','$2a$10$X4LPLAX4xUt16aQ6b7LiGe1S3T2WNotP4E2hvymfkybdN5.vapdPO','USER','loulou','loulou.profile.geneve-lune.jpg');
/*!40000 ALTER TABLE `utilisateur` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-08  7:33:20
