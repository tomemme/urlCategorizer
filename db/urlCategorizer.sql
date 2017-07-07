-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.2.6-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for urlcategorizer
CREATE DATABASE IF NOT EXISTS `urlcategorizer` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `urlcategorizer`;

-- Dumping structure for table urlcategorizer.category
CREATE TABLE IF NOT EXISTS `category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `category_name` varchar(40) NOT NULL,
  PRIMARY KEY (`category_id`),
  KEY `user_category_fk` (`user_id`),
  CONSTRAINT `user_category_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table urlcategorizer.category: ~0 rows (approximately)
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;

-- Dumping structure for table urlcategorizer.link
CREATE TABLE IF NOT EXISTS `link` (
  `link_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL,
  `link_url` varchar(900) NOT NULL,
  PRIMARY KEY (`link_id`),
  KEY `category_links_fk` (`category_id`),
  CONSTRAINT `category_links_fk` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table urlcategorizer.link: ~0 rows (approximately)
/*!40000 ALTER TABLE `link` DISABLE KEYS */;
/*!40000 ALTER TABLE `link` ENABLE KEYS */;

-- Dumping structure for table urlcategorizer.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_created` datetime NOT NULL,
  `user_username` varchar(30) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `password` varchar(25) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table urlcategorizer.user: ~1 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `user_created`, `user_username`, `first_name`, `last_name`, `password`) VALUES
	(1, '2017-07-05 00:00:00', 'tomemme', 'Thomas', 'Emmmerling', 'Passw0rd'),
	(2, '2017-07-06 16:24:47', 'johnnyboy', 'john', 'john', 'Passw0rd');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table urlcategorizer.viewhistory
CREATE TABLE IF NOT EXISTS `viewhistory` (
  `viewhistory_id` int(11) NOT NULL AUTO_INCREMENT,
  `link_id` int(11) NOT NULL,
  `viewhistory_counter` int(11) NOT NULL,
  `viewhistory_rating` smallint(6) NOT NULL,
  `viewhistory_comments` varchar(100) NOT NULL,
  `viewhistory_watchby` date NOT NULL,
  PRIMARY KEY (`viewhistory_id`),
  KEY `links_viewhistory_fk` (`link_id`),
  CONSTRAINT `links_viewhistory_fk` FOREIGN KEY (`link_id`) REFERENCES `link` (`link_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table urlcategorizer.viewhistory: ~0 rows (approximately)
/*!40000 ALTER TABLE `viewhistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `viewhistory` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
