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
INSERT INTO `hibernate_sequence` VALUES (34);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
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
INSERT INTO `img_produit` VALUES (14,'1.png'),(15,'Abraxas.jpg'),(16,'Pants Shitters.jpg'),(20,'Marginalia.jpg'),(18,'Debut.jpg'),(21,'Absolve.jpg'),(24,'Night Sketches.jpg'),(25,'... I Care Because You Do.jpg'),(26,'Intercepted Message.jpg'),(27,'0.9.jpg'),(28,'On & On US CD single.jpg'),(33,'Baduizm.jpg');
/*!40000 ALTER TABLE `img_produit` ENABLE KEYS */;
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
  `meta_id` bigint DEFAULT NULL,
  `album` varchar(255) DEFAULT NULL,
  `annee` int NOT NULL,
  `artiste` varchar(255) DEFAULT NULL,
  `genres` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkcj7ks55s6rr9xy6i3uapaocd` (`meta_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produits`
--

LOCK TABLES `produits` WRITE;
/*!40000 ALTER TABLE `produits` DISABLE KEYS */;
INSERT INTO `produits` VALUES (15,'Santana Abraxas, acquit en 1980, Bonne état mais la piste 2 saute de temp en temp',25,'VINYLE33',NULL,'Abraxas',1970,'Santana','ROCK,FOLK','Santana Abraxas (full Album)'),(14,'1',1,'CD',NULL,'1',1,'1','JAZZ','1'),(16,'Huit mois après leur premier EP, le supergroupe electronica TASTE revient avec leur successeur intitulé Pants Shitters.',25,'VINYLE33',NULL,'Pants Shitters',2023,'Taste','ROCK,ELECTRO_SWING','Le nouvel album du groupe Taste déjà dispo en vinyle ! '),(20,'Includ Marginalia\'s songs from 1 to 40',60,'VINYLE33',NULL,'Marginalia',2018,'Masakatsu Takagi','CONTEMPORARY_JAZZ','Marginalia by the japanase Masakatsu Takagi 高木 正勝'),(18,'Ci kow juroom benneel gaal gi Bjork defoon ci atum 1993',5,'CD',NULL,'Debut',1993,'Bjork','EXPERIMENTAL,VOCAL_TRANCE','Album gu nekk ci yoon wi !'),(21,'Unique K7 du nouveau single de Jacques, une véritable pépite !',2001,'K7',NULL,'Absolve',2023,'Jacques','EXPERIMENTAL,ELECTRO_SWING','Absolve by Jacques (Single version)'),(24,'Tracklist :  1. You & I 2. Theatrical State Of Mind 3. About Felix 4. Bubbles 5. Pacific Telephone 6. Good For Nothing and more !',10,'CD',NULL,'Night Sketches',2019,'Papooz','POP,ELECTRONIC,DANCE','Night Sketches - Papooz (french pop)'),(25,'Parfait état. Assez difficile à trouver en France. ',77,'VINYLE33',NULL,'... I Care Because You Do',1995,'Aphex Twin','ELECTRONIC,EXPERIMENTAL,AVANT_GARDE,ALTERNATIVE','Album published in 1995 by Aphex Twin'),(26,'Caractéristiques :  Vinyle Support, 33 T 30 CM Poids, 380 g, Largeur 313 mm, épaisseur 8 mm, Hauteur 313 mm ',34,'VINYLE33',NULL,'Intercepted Message',2023,'Osees','UK_GARAGE','Very new album aviable now '),(27,'Single version du titre 0.9 issu de l\'album éponyme du DUC',92,'CD',NULL,'0.9',2008,'Booba','HIP_HOP_RAP,GANGSTA_RAP','0.9 Booba (Single)'),(28,'On & On  (album version) 3:45 \r\nBaduizm snippets (\"Otherside of the Game\"/\"Next Lifetime\"/\"Sometimes...\"/\"4 Leaf Clover\") 5:08 \r\nby Erykah Badu \r\nSingle \r\nCD\r\n\r\nSome scratch on cover',5,'CD',NULL,'On & On US CD single',1996,'Erykah Badu','RNB_SOUL,NEO_SOUL','On & On US CD single'),(33,'Baduizm état catastrophique ☠ je l\'ai détruit dans le lecteur cd de mon auto en mettant un cd de Mpokora\r\n\r\n1.	\"Rimshot (Intro)\"	Erykah Badu, Madukwu Chinwah	Chinwah	1:56\r\n2.	\"On & On\"	Badu, Jahmal \"JaBorn\" Cantero	Bob Power, JaBorn	3:45\r\n3.	\"Appletree\"	Badu, Robert Bradford	Ike Lee III, Badu	4:25\r\n4.	\"Otherside of the Game\"	Badu, Questlove, Richard Nichols, James Poyser	The Roots, Nichols	6:33\r\n5.	\"Sometimes (Mix #9)\"	Badu, The Roots, Nichols, Poyser	The Roots, Nichols, Poyser	0:44\r\n6.	\"Next Lifetime\"	Badu, Anthony Scott	Tone the Backbone	6:26\r\n7.	\"Afro (Freestyle Skit)\"	Badu, Poyser, Jafar Barron	Badu, Poyser, Barron	2:04\r\n8.	\"Certainly\"	Badu, Chinwah	Chinwah	4:43\r\n9.	\"4 Leaf Clover\"	David Lewis, Wayne Lewis	Ike Lee III, Badu	4:34\r\n10.	\"No Love\"	Badu, Bradford	Bradford	5:08\r\n11.	\"Drama\"	Badu, Tyallen Macklin	Bob Power	6:02\r\n12.	\"Sometimes...\"	Badu, The Roots, Nichols, Poyser	The Roots, Nichols, Poyser	4:10\r\n13.	\"Certainly (Flipped It)\"	Badu, Chinwah	Chinwah	5:26\r\n14.	\"Rimshot (Outro)\"	Badu, Chinwah	Chinwah	2:19\r\nTotal length:	58:151.	\"Rimshot (Intro)\"	Erykah Badu, Madukwu Chinwah	Chinwah	1:56\r\n2.	\"On & On\"	Badu, Jahmal \"JaBorn\" Cantero	Bob Power, JaBorn	3:45\r\n3.	\"Appletree\"	Badu, Robert Bradford	Ike Lee III, Badu	4:25\r\n4.	\"Otherside of the Game\"	Badu, Questlove, Richard Nichols, James Poyser	The Roots, Nichols	6:33\r\n5.	\"Sometimes (Mix #9)\"	Badu, The Roots, Nichols, Poyser	The Roots, Nichols, Poyser	0:44\r\n6.	\"Next Lifetime\"	Badu, Anthony Scott	Tone the Backbone	6:26\r\n7.	\"Afro (Freestyle Skit)\"	Badu, Poyser, Jafar Barron	Badu, Poyser, Barron	2:04\r\n8.	\"Certainly\"	Badu, Chinwah	Chinwah	4:43\r\n9.	\"4 Leaf Clover\"	David Lewis, Wayne Lewis	Ike Lee III, Badu	4:34\r\n10.	\"No Love\"	Badu, Bradford	Bradford	5:08\r\n11.	\"Drama\"	Badu, Tyallen Macklin	Bob Power	6:02\r\n12.	\"Sometimes...\"	Badu, The Roots, Nichols, Poyser	The Roots, Nichols, Poyser	4:10\r\n13.	\"Certainly (Flipped It)\"	Badu, Chinwah	Chinwah	5:26\r\n14.	\"Rimshot (Outro)\"	Badu, Chinwah	Chinwah	2:19\r\nTotal length:	58:15',1,'CD',NULL,'Baduizm',1997,'Erykah Badu','RNB_SOUL,NEO_SOUL','Baduizm album complet(ement cassé)');
/*!40000 ALTER TABLE `produits` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-29  3:35:51
