-- MariaDB dump 10.19  Distrib 10.5.12-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: starmonydb
-- ------------------------------------------------------
-- Server version	10.5.12-MariaDB-0+deb11u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chord`
--

DROP TABLE IF EXISTS `chord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chord` (
  `id_chord` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  `symbol` varchar(65) NOT NULL,
  `code` varchar(50) NOT NULL,
  PRIMARY KEY (`id_chord`),
  UNIQUE KEY `id_chord_UNIQUE` (`id_chord`),
  UNIQUE KEY `intervals_UNIQUE` (`code`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `symbol` (`symbol`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chord`
--

LOCK TABLES `chord` WRITE;
/*!40000 ALTER TABLE `chord` DISABLE KEYS */;
INSERT INTO `chord` VALUES (1,'quinta','5','1-5'),(2,'mayor','-','1-3-5'),(3,'menor','m','1-3b-5'),(4,'aumentada','+','1-3-5#'),(5,'disminuida','dim','1-3b-5b'),(6,'suspendida','sus4','1-4-5'),(7,'con segunda suspendida','sus2','1-2-5'),(8,'séptima','7','1-3-5-7b'),(9,'séptima con quinta disminuida','7♭5','1-3-5b-7b'),(10,'séptima con quinta aumentada','7♯5','1-3-5#-7b'),(11,'séptima con cuarta suspendida','7sus4','1-4-5-7b'),(12,'menor séptima','m7','1-3b-5-7b'),(13,'menor séptima con quinta bemol','m7♭5','1-3b-5b-7b'),(14,'menor séptima con quinta aumentada','m7♯5','1-3b-5#-7b'),(15,'sexta','6','1-3-5-6'),(16,'menor sexta','m6','1-3b-5-6'),(17,'sexta con cuarta suspendida','6sus4','1-4-5-6'),(18,'séptima mayor','Maj7','1-3-5-7'),(19,'menor séptima mayor','mMaj7','1-3b-5-7'),(20,'sexta con novena (añadida)','6/9','1-3-5-6-9'),(21,'menor sexta con novena (añadida)','m6/9','1-3b-5-6-9'),(22,'séptima con novena bemol (añadida)','7♭9','1-3-5-7b-9b'),(23,'séptima con novena aumentada (añadida)','7♯9','1-3-5-7b-9#'),(24,'menor séptima con novena bemol (añadida)','m7♭9','1-3b-5-7b-9b'),(25,'séptima con oncena (añadida)','7add11','1-3-5-7b-11'),(26,'séptima con trecena (añadida)','7add13','1-3-5-7b-13'),(27,'novena','9','1-3-5-7b-9'),(28,'menor novena','m9','1-3b-5-7b-9'),(29,'novena con quinta bemol','9♭5','1-3-5b-7b-9'),(30,'novena con quinta aumentada','9♯5','1-3-5#-7b-9'),(31,'novena con cuarta suspendida','9sus4','1-4-5-7b-9'),(32,'mayor novena','Maj9','1-3-5-7-9'),(33,'menor novena mayor','mMaj9','1-3b-5-7-9'),(34,'novena con sexta (añadida)','9/6','1-3-5-6-7b-9'),(35,'novena con oncena aumentada (añadida)','9♯11','1-3-5-7b-9-11#'),(36,'novena mayor con undécima aumentada (añadida)','Maj9♯11','1-3-5-7-9-11#'),(37,'undécima','11','1-3-5-7b-9-11'),(38,'menor undécima','m11','1-3b-5-7b-9-11'),(39,'undécima con novena bemol','11♭9','1-3-5-7b-9b-11'),(40,'undécima con novena aumentada','11♯9','1-3-5-7b-9#-11'),(41,'undécima mayor','Maj11','1-3-5-7-9-11'),(42,'menor undécima mayor','mMaj11','1-3b-5-7-9-11'),(43,'menor decimotercera','m13','1-3b-5-7b-9-11-13'),(44,'decimotercera con cuarta suspendida','13sus4','1-4-5-7b-9-11-13'),(45,'decimotercera con quinta bemol','13♭5','1-3-5b-7b-9-11-13'),(46,'decimotercera con quinta aumentada','13♯5','1-3-5#-7b-9-11-13'),(47,'treceava con novena bemol','13♭9','1-3-5-7b-9b-11-13'),(48,'decimotercera con novena aumentada','13♯9','1-3-5-7b-9#-11-13'),(49,'decimotercera con quinta y novena bemoles','13♭5♭9','1-3-5b-7b-9b-11-13'),(50,'decimotercera con quinta bemol y novena aumentada','13♭5♯9','1-3-5b-7b-9#-11-13'),(51,'decimotercera con quinta aumentada y novena bemol','13♯5♭9','1-3-5#-7b-9b-11-13'),(52,'decimotercera con quinta y novena aumentadas','13♯5♯9','1-3-5#-7b-9#-11-13'),(53,'decimotercera mayor','Maj13','1-3-5-7-9-11-13'),(54,'menor decimotercera mayor','mMaj13','1-3b-5-7-9-11-13'),(55,'decimotercera mayor con quinta bemol','Maj13♭5','1-3-5b-7-9-11-13'),(56,'decimotercera mayor con quinta aumentada','Maj13♯5','1-3-5#-7-9-11-13'),(57,'decimotercera mayor con novena bemol','Maj13♭9','1-3-5-7-9b-11-13'),(58,'decimotercera mayor con novena aumentada','Maj13♯9','1-3-5-7-9#-11-13'),(59,'decimotercera mayor con quinta y novena bemoles','Maj13♭5♭9','1-3-5b-7-9b-11-13'),(60,'decimotercera mayor con quinta bemol y novena aumentada','Maj13♭5♯9','1-3-5b-7-9#-11-13'),(61,'decimotercera mayor con quinta aumentada y novena bemol','Maj13♯5♭9','1-3-5#-7-9b-11-13'),(62,'decimotercera mayor con quinta y novena aumentadas','Maj13♯5♯9','1-3-5#-7-9#-11-13');
/*!40000 ALTER TABLE `chord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chord_has_tag`
--

DROP TABLE IF EXISTS `chord_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chord_has_tag` (
  `chord_id_chord` int(11) NOT NULL,
  `tag_id_tag` int(11) NOT NULL,
  PRIMARY KEY (`chord_id_chord`,`tag_id_tag`),
  KEY `fk_chord_has_tag_tag1_idx` (`tag_id_tag`),
  KEY `fk_chord_has_tag_chord1_idx` (`chord_id_chord`),
  CONSTRAINT `fk_chord_has_tag_chord1` FOREIGN KEY (`chord_id_chord`) REFERENCES `chord` (`id_chord`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_chord_has_tag_tag1` FOREIGN KEY (`tag_id_tag`) REFERENCES `tag` (`id_tag`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chord_has_tag`
--

LOCK TABLES `chord_has_tag` WRITE;
/*!40000 ALTER TABLE `chord_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `chord_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `concrete_progression`
--

DROP TABLE IF EXISTS `concrete_progression`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `concrete_progression` (
  `chord_id_chord` int(11) NOT NULL,
  `progression_id_progression` int(11) NOT NULL,
  `grade` varchar(10) NOT NULL,
  PRIMARY KEY (`chord_id_chord`,`progression_id_progression`,`grade`),
  KEY `fk_chord_has_progression_progression1_idx` (`progression_id_progression`),
  KEY `fk_chord_has_progression_chord1_idx` (`chord_id_chord`),
  CONSTRAINT `fk_chord_has_progression_chord1` FOREIGN KEY (`chord_id_chord`) REFERENCES `chord` (`id_chord`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_chord_has_progression_progression1` FOREIGN KEY (`progression_id_progression`) REFERENCES `progression` (`id_progression`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concrete_progression`
--

LOCK TABLES `concrete_progression` WRITE;
/*!40000 ALTER TABLE `concrete_progression` DISABLE KEYS */;
/*!40000 ALTER TABLE `concrete_progression` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `concrete_scale`
--

DROP TABLE IF EXISTS `concrete_scale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `concrete_scale` (
  `note_id_note` int(11) NOT NULL,
  `scale_id_scale` int(11) NOT NULL,
  PRIMARY KEY (`note_id_note`,`scale_id_scale`),
  KEY `fk_note_has_scale_scale1_idx` (`scale_id_scale`),
  KEY `fk_note_has_scale_note1_idx` (`note_id_note`),
  CONSTRAINT `fk_note_has_scale_note1` FOREIGN KEY (`note_id_note`) REFERENCES `note` (`id_note`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_note_has_scale_scale1` FOREIGN KEY (`scale_id_scale`) REFERENCES `scale` (`id_scale`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concrete_scale`
--

LOCK TABLES `concrete_scale` WRITE;
/*!40000 ALTER TABLE `concrete_scale` DISABLE KEYS */;
/*!40000 ALTER TABLE `concrete_scale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interval_has_chord`
--

DROP TABLE IF EXISTS `interval_has_chord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interval_has_chord` (
  `interval_id_interval` int(11) NOT NULL,
  `chord_id_chord` int(11) NOT NULL,
  PRIMARY KEY (`interval_id_interval`,`chord_id_chord`),
  KEY `fk_interval_has_chord_chord1_idx` (`chord_id_chord`),
  KEY `fk_interval_has_chord_interval1_idx` (`interval_id_interval`),
  CONSTRAINT `fk_interval_has_chord_chord1` FOREIGN KEY (`chord_id_chord`) REFERENCES `chord` (`id_chord`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_interval_has_chord_interval1` FOREIGN KEY (`interval_id_interval`) REFERENCES `intervals` (`id_interval`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interval_has_chord`
--

LOCK TABLES `interval_has_chord` WRITE;
/*!40000 ALTER TABLE `interval_has_chord` DISABLE KEYS */;
INSERT INTO `interval_has_chord` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25),(1,26),(1,27),(1,28),(1,29),(1,30),(1,31),(1,32),(1,33),(1,34),(1,35),(1,36),(1,37),(1,38),(1,39),(1,40),(1,41),(1,42),(1,43),(1,44),(1,45),(1,46),(1,47),(1,48),(1,49),(1,50),(1,51),(1,52),(1,53),(1,54),(1,55),(1,56),(1,57),(1,58),(1,59),(1,60),(1,61),(1,62),(2,7),(4,3),(4,5),(4,12),(4,13),(4,14),(4,16),(4,19),(4,21),(4,24),(4,28),(4,33),(4,38),(4,42),(4,43),(4,54),(5,2),(5,4),(5,8),(5,9),(5,10),(5,15),(5,18),(5,20),(5,22),(5,23),(5,25),(5,26),(5,27),(5,29),(5,30),(5,32),(5,34),(5,35),(5,36),(5,37),(5,39),(5,40),(5,41),(5,45),(5,46),(5,47),(5,48),(5,49),(5,50),(5,51),(5,52),(5,53),(5,55),(5,56),(5,57),(5,58),(5,59),(5,60),(5,61),(5,62),(6,6),(6,11),(6,17),(6,31),(6,44),(7,5),(7,9),(7,13),(7,29),(7,45),(7,49),(7,50),(7,55),(7,59),(7,60),(8,1),(8,2),(8,3),(8,6),(8,7),(8,8),(8,11),(8,12),(8,15),(8,16),(8,17),(8,18),(8,19),(8,20),(8,21),(8,22),(8,23),(8,24),(8,25),(8,26),(8,27),(8,28),(8,31),(8,32),(8,33),(8,34),(8,35),(8,36),(8,37),(8,38),(8,39),(8,40),(8,41),(8,42),(8,43),(8,44),(8,47),(8,48),(8,53),(8,54),(8,57),(8,58),(9,4),(9,10),(9,14),(9,30),(9,46),(9,51),(9,52),(9,56),(9,57),(9,58),(9,59),(9,60),(9,61),(9,62),(10,15),(10,16),(10,17),(10,20),(10,21),(10,34),(11,8),(11,9),(11,10),(11,11),(11,12),(11,13),(11,14),(11,22),(11,23),(11,24),(11,25),(11,26),(11,27),(11,28),(11,29),(11,30),(11,31),(11,34),(11,35),(11,37),(11,38),(11,39),(11,40),(11,43),(11,44),(11,45),(11,46),(11,47),(11,48),(11,49),(11,50),(11,51),(11,52),(12,18),(12,19),(12,32),(12,33),(12,36),(12,41),(12,42),(12,53),(12,54),(12,55),(12,56),(12,57),(12,58),(12,59),(12,60),(12,61),(12,62),(14,22),(14,24),(14,39),(14,47),(14,49),(14,51),(14,57),(14,59),(14,61),(15,20),(15,21),(15,27),(15,28),(15,29),(15,30),(15,31),(15,32),(15,33),(15,34),(15,35),(15,36),(15,37),(15,38),(15,41),(15,42),(15,43),(15,44),(15,45),(15,46),(15,53),(15,54),(15,55),(15,56),(16,23),(16,40),(16,48),(16,50),(16,52),(16,58),(16,60),(16,62),(18,25),(18,37),(18,38),(18,39),(18,40),(18,41),(18,42),(18,43),(18,44),(18,45),(18,46),(18,47),(18,48),(18,49),(18,50),(18,51),(18,52),(18,53),(18,54),(18,55),(19,35),(19,36),(22,26),(22,43),(22,44),(22,45),(22,46),(22,47),(22,48),(22,49),(22,50),(22,51),(22,52),(22,53),(22,54),(22,55),(22,56),(22,57),(22,58),(22,59),(22,60),(22,61),(22,62);
/*!40000 ALTER TABLE `interval_has_chord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interval_has_tag`
--

DROP TABLE IF EXISTS `interval_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interval_has_tag` (
  `interval_id_interval` int(11) NOT NULL,
  `tag_id_tag` int(11) NOT NULL,
  PRIMARY KEY (`interval_id_interval`,`tag_id_tag`),
  KEY `fk_interval_has_tag_tag1_idx` (`tag_id_tag`),
  KEY `fk_interval_has_tag_interval1_idx` (`interval_id_interval`),
  CONSTRAINT `fk_interval_has_tag_interval1` FOREIGN KEY (`interval_id_interval`) REFERENCES `intervals` (`id_interval`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_interval_has_tag_tag1` FOREIGN KEY (`tag_id_tag`) REFERENCES `tag` (`id_tag`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interval_has_tag`
--

LOCK TABLES `interval_has_tag` WRITE;
/*!40000 ALTER TABLE `interval_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `interval_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `intervals`
--

DROP TABLE IF EXISTS `intervals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `intervals` (
  `id_interval` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `symbol` varchar(20) NOT NULL,
  `semitones` int(11) NOT NULL,
  PRIMARY KEY (`id_interval`),
  UNIQUE KEY `id_interval_UNIQUE` (`id_interval`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `intervals`
--

LOCK TABLES `intervals` WRITE;
/*!40000 ALTER TABLE `intervals` DISABLE KEYS */;
INSERT INTO `intervals` VALUES (1,'Unísono','-',0),(2,'segunda menor','2b',1),(3,'segunda mayor','2',2),(4,'tercera  menor','3b',3),(5,'tercera  mayor','3',4),(6,'cuarta justa','4',5),(7,'cuarta aumentada/quinta disminuida','4#/5b',6),(8,'quinta justa','5',7),(9,'quinta aumentada/sexta menor','5#/6b',8),(10,'sexta mayor','6',9),(11,'septima menor','7b',10),(12,'septima mayor','7',11),(13,'octava justa','8',12),(14,'novena menor','9b',13),(15,'novena mayor','9',14),(16,'novena aumentada/decima menor','9#/10b',15),(17,'decima mayor/onceava disminuida','10/11b',16),(18,'onceava justa','11',17),(19,'onceava aumentada','11#',18),(20,'doceava justa','12',19),(21,'treceava menor','13b',20),(22,'treceava mayor','13',21),(23,'treceava aumentada','13#',22);
/*!40000 ALTER TABLE `intervals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `note`
--

DROP TABLE IF EXISTS `note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `note` (
  `id_note` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `symbol` varchar(45) NOT NULL,
  PRIMARY KEY (`id_note`),
  UNIQUE KEY `id_notes_UNIQUE` (`id_note`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `symbol_UNIQUE` (`symbol`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `note`
--

LOCK TABLES `note` WRITE;
/*!40000 ALTER TABLE `note` DISABLE KEYS */;
INSERT INTO `note` VALUES (1,'do','C'),(2,'do sostenido','C#'),(3,'re','D'),(4,'re sostenido','D#'),(5,'mi','E'),(6,'fa','F'),(7,'fa sostenido','F#'),(8,'sol','G'),(9,'sol sostenido','G#'),(10,'la','A'),(11,'la sostenido','A#'),(12,'si','B'),(13,'do bemol','Cb'),(14,'re bemol','Db'),(15,'mi bemol','Eb'),(16,'mi sostenido','E#'),(17,'fa bemol','Fb'),(18,'sol bemol','Gb'),(19,'la bemol','Ab'),(20,'si bemol','Bb'),(21,'si sostenido','B#');
/*!40000 ALTER TABLE `note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notes_has_interval`
--

DROP TABLE IF EXISTS `notes_has_interval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notes_has_interval` (
  `interval_id_interval` int(11) NOT NULL,
  `note_id_note1` int(11) NOT NULL,
  `note_id_note2` int(11) NOT NULL,
  PRIMARY KEY (`interval_id_interval`,`note_id_note1`,`note_id_note2`),
  KEY `fk_notes_has_interval_interval1_idx` (`interval_id_interval`),
  KEY `fk_notes_has_interval_note1_idx` (`note_id_note1`),
  KEY `fk_notes_has_interval_note2_idx` (`note_id_note2`),
  CONSTRAINT `fk_notes_has_interval_interval1` FOREIGN KEY (`interval_id_interval`) REFERENCES `intervals` (`id_interval`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_notes_has_interval_note1` FOREIGN KEY (`note_id_note1`) REFERENCES `note` (`id_note`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_notes_has_interval_note2` FOREIGN KEY (`note_id_note2`) REFERENCES `note` (`id_note`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notes_has_interval`
--

LOCK TABLES `notes_has_interval` WRITE;
/*!40000 ALTER TABLE `notes_has_interval` DISABLE KEYS */;
INSERT INTO `notes_has_interval` VALUES (1,1,1),(1,2,2),(1,3,3),(1,4,4),(1,5,5),(1,6,6),(1,7,7),(1,8,8),(1,9,9),(1,10,10),(1,11,11),(1,12,12),(2,1,2),(2,2,1),(2,2,3),(2,3,2),(2,3,4),(2,4,3),(2,4,5),(2,5,4),(2,5,6),(2,6,5),(2,6,7),(2,7,6),(2,7,8),(2,8,7),(2,8,9),(2,9,8),(2,9,10),(2,10,9),(2,10,11),(2,11,10),(2,11,12),(2,12,11),(3,1,3),(3,2,4),(3,3,1),(3,3,5),(3,4,2),(3,4,6),(3,5,3),(3,5,7),(3,6,4),(3,6,8),(3,7,5),(3,7,9),(3,8,6),(3,8,10),(3,9,7),(3,9,11),(3,10,8),(3,10,12),(3,11,9),(3,12,10),(4,1,4),(4,2,5),(4,3,6),(4,4,1),(4,4,7),(4,5,2),(4,5,8),(4,6,3),(4,6,9),(4,7,4),(4,7,10),(4,8,5),(4,8,11),(4,9,6),(4,9,12),(4,10,7),(4,11,8),(4,12,9),(5,1,5),(5,2,6),(5,3,7),(5,4,8),(5,5,1),(5,5,9),(5,6,2),(5,6,10),(5,7,3),(5,7,11),(5,8,4),(5,8,12),(5,9,5),(5,10,6),(5,11,7),(5,12,8),(6,1,6),(6,2,7),(6,3,8),(6,4,9),(6,5,10),(6,6,1),(6,6,11),(6,7,2),(6,7,12),(6,8,3),(6,9,4),(6,10,5),(6,11,6),(6,12,7),(7,1,7),(7,2,8),(7,3,9),(7,4,10),(7,5,11),(7,6,12),(7,7,1),(7,8,2),(7,9,3),(7,10,4),(7,11,5),(7,12,6),(8,1,7),(8,2,8),(8,3,9),(8,4,10),(8,5,11),(8,6,12),(8,7,1),(8,8,2),(8,9,3),(8,10,4),(8,11,5),(8,12,6),(9,1,7),(9,2,8),(9,3,9),(9,4,10),(9,5,11),(9,6,12),(9,7,1),(9,8,2),(9,9,3),(9,10,4),(9,11,5),(9,12,6),(10,1,8),(10,2,9),(10,3,10),(10,4,11),(10,5,12),(10,8,1),(10,9,2),(10,10,3),(10,11,4),(10,12,5),(11,1,9),(11,2,10),(11,3,11),(11,4,12),(11,9,1),(11,10,2),(11,11,3),(11,12,4),(12,1,9),(12,2,10),(12,3,11),(12,4,12),(12,9,1),(12,10,2),(12,11,3),(12,12,4),(13,1,10),(13,2,11),(13,3,12),(13,10,1),(13,11,2),(13,12,3),(14,1,11),(14,2,12),(14,11,1),(14,12,2),(15,1,12),(15,12,1);
/*!40000 ALTER TABLE `notes_has_interval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preference_tag`
--

DROP TABLE IF EXISTS `preference_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preference_tag` (
  `profile_id_profile` int(11) NOT NULL,
  `tag_id_tag` int(11) NOT NULL,
  `value` double NOT NULL,
  PRIMARY KEY (`profile_id_profile`,`tag_id_tag`),
  KEY `fk_profile_has_tag_tag1_idx` (`tag_id_tag`),
  KEY `fk_profile_has_tag_profile1_idx` (`profile_id_profile`),
  CONSTRAINT `fk_profile_has_tag_profile1` FOREIGN KEY (`profile_id_profile`) REFERENCES `profile` (`id_profile`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_profile_has_tag_tag1` FOREIGN KEY (`tag_id_tag`) REFERENCES `tag` (`id_tag`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preference_tag`
--

LOCK TABLES `preference_tag` WRITE;
/*!40000 ALTER TABLE `preference_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `preference_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile` (
  `id_profile` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_nacimiento` date NOT NULL,
  `state_id_state` int(11) NOT NULL,
  PRIMARY KEY (`id_profile`),
  UNIQUE KEY `id_profile_UNIQUE` (`id_profile`),
  KEY `fk_profile_state1_idx` (`state_id_state`),
  CONSTRAINT `fk_profile_state1` FOREIGN KEY (`state_id_state`) REFERENCES `state` (`id_state`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `progression`
--

DROP TABLE IF EXISTS `progression`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `progression` (
  `id_progression` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `code` varchar(50) NOT NULL,
  `symbol` varchar(50) NOT NULL,
  PRIMARY KEY (`id_progression`),
  UNIQUE KEY `id_progression_UNIQUE` (`id_progression`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  UNIQUE KEY `symbol` (`symbol`),
  UNIQUE KEY `symbol_2` (`symbol`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `progression`
--

LOCK TABLES `progression` WRITE;
/*!40000 ALTER TABLE `progression` DISABLE KEYS */;
INSERT INTO `progression` VALUES (1,'-','1-5-4-1','I-V-IV-I'),(2,'-','1-5','I-V'),(3,'-','1-5-6m-4','I-V-VIm-IV'),(4,'-','1-3-4-4m','I-III-IV-IVm'),(5,'-','2m-4-6m-5','IIm-IV-VIm-V'),(6,'rock & blues','1-4-5','I-IV-V'),(7,'blues de 12 compases','1-1-1-1-4-4-1-1-5-5-1-1','I-I-I-IV-IV-I-I-V-V-I-I'),(8,'base jazz','2m-5-1','IIm-V-I'),(9,'base jazz con sexta','1-6m-2m-5','I-VIm-IIm-V'),(10,'acordes trIstes','1m-7-6-7-1m','Im-VII-VI-VII-Im'),(11,'acordes alegres','1-4-5-57','I-IV-V-V7');
/*!40000 ALTER TABLE `progression` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `progression_has_tag`
--

DROP TABLE IF EXISTS `progression_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `progression_has_tag` (
  `progression_id_progression` int(11) NOT NULL,
  `tag_id_tag` int(11) NOT NULL,
  PRIMARY KEY (`progression_id_progression`,`tag_id_tag`),
  KEY `fk_progression_has_tag_tag1_idx` (`tag_id_tag`),
  KEY `fk_progression_has_tag_progression1_idx` (`progression_id_progression`),
  CONSTRAINT `fk_progression_has_tag_progression1` FOREIGN KEY (`progression_id_progression`) REFERENCES `progression` (`id_progression`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_progression_has_tag_tag1` FOREIGN KEY (`tag_id_tag`) REFERENCES `tag` (`id_tag`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `progression_has_tag`
--

LOCK TABLES `progression_has_tag` WRITE;
/*!40000 ALTER TABLE `progression_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `progression_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scale`
--

DROP TABLE IF EXISTS `scale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scale` (
  `id_scale` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `symbol` varchar(20) NOT NULL,
  `code` varchar(50) NOT NULL,
  PRIMARY KEY (`id_scale`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `id_scale_UNIQUE` (`id_scale`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scale`
--

LOCK TABLES `scale` WRITE;
/*!40000 ALTER TABLE `scale` DISABLE KEYS */;
INSERT INTO `scale` VALUES (1,'jónico','jónico','2–2–1–2–2–2–1'),(2,'dórico','dórico','2–1–2–2–2–1–2'),(3,'frigio','frigio','1–2–2–2–1–2–2'),(4,'lidio','lidio','2–2–2–1–2–2–1'),(5,'mixolidio','mixolidio','2–2–1–2–2–1–2'),(6,'eólico','eólico','2–1–2–2–1–2–2'),(7,'locrio','locrio','1–2–2–1–2–2–2');
/*!40000 ALTER TABLE `scale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scale_grades`
--

DROP TABLE IF EXISTS `scale_grades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scale_grades` (
  `chord_id_chord` int(11) NOT NULL,
  `scale_id_scale` int(11) NOT NULL,
  `grade` varchar(10) NOT NULL,
  PRIMARY KEY (`chord_id_chord`,`scale_id_scale`,`grade`),
  KEY `fk_chord_has_scale_scale1_idx` (`scale_id_scale`),
  KEY `fk_chord_has_scale_chord1_idx` (`chord_id_chord`),
  CONSTRAINT `fk_chord_has_scale_chord1` FOREIGN KEY (`chord_id_chord`) REFERENCES `chord` (`id_chord`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_chord_has_scale_scale1` FOREIGN KEY (`scale_id_scale`) REFERENCES `scale` (`id_scale`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scale_grades`
--

LOCK TABLES `scale_grades` WRITE;
/*!40000 ALTER TABLE `scale_grades` DISABLE KEYS */;
INSERT INTO `scale_grades` VALUES (2,1,'I'),(2,1,'IV'),(2,1,'V'),(2,6,'III'),(2,6,'VI'),(2,6,'VII'),(3,1,'II'),(3,1,'III'),(3,1,'VI'),(3,6,'I'),(3,6,'IV'),(3,6,'V'),(5,1,'VII'),(5,6,'II');
/*!40000 ALTER TABLE `scale_grades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scale_has_tag`
--

DROP TABLE IF EXISTS `scale_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scale_has_tag` (
  `scale_id_scale` int(11) NOT NULL,
  `tag_id_tag` int(11) NOT NULL,
  PRIMARY KEY (`scale_id_scale`,`tag_id_tag`),
  KEY `fk_scale_has_tag_tag1_idx` (`tag_id_tag`),
  KEY `fk_scale_has_tag_scale1_idx` (`scale_id_scale`),
  CONSTRAINT `fk_scale_has_tag_scale1` FOREIGN KEY (`scale_id_scale`) REFERENCES `scale` (`id_scale`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_scale_has_tag_tag1` FOREIGN KEY (`tag_id_tag`) REFERENCES `tag` (`id_tag`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scale_has_tag`
--

LOCK TABLES `scale_has_tag` WRITE;
/*!40000 ALTER TABLE `scale_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `scale_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `state` (
  `id_state` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id_state`),
  UNIQUE KEY `id_state_UNIQUE` (`id_state`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `state`
--

LOCK TABLES `state` WRITE;
/*!40000 ALTER TABLE `state` DISABLE KEYS */;
/*!40000 ALTER TABLE `state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `id_status` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `desciption` varchar(100) NOT NULL,
  PRIMARY KEY (`id_status`),
  UNIQUE KEY `id_status_UNIQUE` (`id_status`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `id_tag` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(255) NOT NULL,
  `value` double NOT NULL,
  PRIMARY KEY (`id_tag`),
  UNIQUE KEY `id_tag_UNIQUE` (`id_tag`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `id_user_UNIQUE` (`id_user`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_chord`
--

DROP TABLE IF EXISTS `user_has_chord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_chord` (
  `user_id_user` int(11) NOT NULL,
  `chord_id_chord` int(11) NOT NULL,
  PRIMARY KEY (`user_id_user`,`chord_id_chord`),
  KEY `fk_user_has_chord_chord1_idx` (`chord_id_chord`),
  KEY `fk_user_has_chord_user1_idx` (`user_id_user`),
  CONSTRAINT `fk_user_has_chord_chord1` FOREIGN KEY (`chord_id_chord`) REFERENCES `chord` (`id_chord`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_chord_user1` FOREIGN KEY (`user_id_user`) REFERENCES `user` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_chord`
--

LOCK TABLES `user_has_chord` WRITE;
/*!40000 ALTER TABLE `user_has_chord` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_has_chord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_interval`
--

DROP TABLE IF EXISTS `user_has_interval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_interval` (
  `user_id_user` int(11) NOT NULL,
  `interval_id_interval` int(11) NOT NULL,
  PRIMARY KEY (`user_id_user`,`interval_id_interval`),
  KEY `fk_user_has_interval_interval1_idx` (`interval_id_interval`),
  KEY `fk_user_has_interval_user1_idx` (`user_id_user`),
  CONSTRAINT `fk_user_has_interval_interval1` FOREIGN KEY (`interval_id_interval`) REFERENCES `intervals` (`id_interval`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_interval_user1` FOREIGN KEY (`user_id_user`) REFERENCES `user` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_interval`
--

LOCK TABLES `user_has_interval` WRITE;
/*!40000 ALTER TABLE `user_has_interval` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_has_interval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_profile`
--

DROP TABLE IF EXISTS `user_has_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_profile` (
  `user_id_user` int(11) NOT NULL,
  `profile_id_profile` int(11) NOT NULL,
  PRIMARY KEY (`user_id_user`,`profile_id_profile`),
  KEY `fk_user_has_profile_profile1_idx` (`profile_id_profile`),
  KEY `fk_user_has_profile_user1_idx` (`user_id_user`),
  CONSTRAINT `fk_user_has_profile_profile1` FOREIGN KEY (`profile_id_profile`) REFERENCES `profile` (`id_profile`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_profile_user1` FOREIGN KEY (`user_id_user`) REFERENCES `user` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_profile`
--

LOCK TABLES `user_has_profile` WRITE;
/*!40000 ALTER TABLE `user_has_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_has_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_progression`
--

DROP TABLE IF EXISTS `user_has_progression`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_progression` (
  `user_id_user` int(11) NOT NULL,
  `progression_id_progression` int(11) NOT NULL,
  PRIMARY KEY (`user_id_user`,`progression_id_progression`),
  KEY `fk_user_has_progression_progression1_idx` (`progression_id_progression`),
  KEY `fk_user_has_progression_user1_idx` (`user_id_user`),
  CONSTRAINT `fk_user_has_progression_progression1` FOREIGN KEY (`progression_id_progression`) REFERENCES `progression` (`id_progression`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_progression_user1` FOREIGN KEY (`user_id_user`) REFERENCES `user` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_progression`
--

LOCK TABLES `user_has_progression` WRITE;
/*!40000 ALTER TABLE `user_has_progression` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_has_progression` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_scale`
--

DROP TABLE IF EXISTS `user_has_scale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_scale` (
  `user_id_user` int(11) NOT NULL,
  `scale_id_scale` int(11) NOT NULL,
  PRIMARY KEY (`user_id_user`,`scale_id_scale`),
  KEY `fk_user_has_scale_scale1_idx` (`scale_id_scale`),
  KEY `fk_user_has_scale_user1_idx` (`user_id_user`),
  CONSTRAINT `fk_user_has_scale_scale1` FOREIGN KEY (`scale_id_scale`) REFERENCES `scale` (`id_scale`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_scale_user1` FOREIGN KEY (`user_id_user`) REFERENCES `user` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_scale`
--

LOCK TABLES `user_has_scale` WRITE;
/*!40000 ALTER TABLE `user_has_scale` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_has_scale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_status`
--

DROP TABLE IF EXISTS `user_has_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_status` (
  `user_id_user` int(11) NOT NULL,
  `status_id_status` int(11) NOT NULL,
  PRIMARY KEY (`user_id_user`,`status_id_status`),
  UNIQUE KEY `user_id_user_UNIQUE` (`user_id_user`),
  KEY `fk_user_has_status1_status1_idx` (`status_id_status`),
  KEY `fk_user_has_status1_user1_idx` (`user_id_user`),
  CONSTRAINT `fk_user_has_status1_status1` FOREIGN KEY (`status_id_status`) REFERENCES `status` (`id_status`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_status1_user1` FOREIGN KEY (`user_id_user`) REFERENCES `user` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_status`
--

LOCK TABLES `user_has_status` WRITE;
/*!40000 ALTER TABLE `user_has_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_has_status` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-27 23:10:17
