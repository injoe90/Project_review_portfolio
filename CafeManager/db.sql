-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.3.11-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- cafe_manager 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `cafe_manager` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `cafe_manager`;

-- 테이블 cafe_manager.buy 구조 내보내기
CREATE TABLE IF NOT EXISTS `buy` (
  `buy_id` int(11) NOT NULL AUTO_INCREMENT,
  `cart_id` int(11) DEFAULT NULL,
  `table_id` int(11) NOT NULL,
  `menu_name2` varchar(20) NOT NULL,
  `payment_price` int(11) NOT NULL,
  PRIMARY KEY (`buy_id`),
  KEY `buy_cart_fk` (`cart_id`),
  CONSTRAINT `buy_cart_fk` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_num`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 테이블 데이터 cafe_manager.buy:~2 rows (대략적) 내보내기
/*!40000 ALTER TABLE `buy` DISABLE KEYS */;
INSERT IGNORE INTO `buy` (`buy_id`, `cart_id`, `table_id`, `menu_name2`, `payment_price`) VALUES
	(1, 1, 1, '아메리카노', 2),
	(2, 2, 1, '카페라떼', 2);
/*!40000 ALTER TABLE `buy` ENABLE KEYS */;

-- 테이블 cafe_manager.cart 구조 내보내기
CREATE TABLE IF NOT EXISTS `cart` (
  `cart_num` int(11) NOT NULL AUTO_INCREMENT,
  `table_id` int(11) NOT NULL,
  `menu_name` varchar(20) NOT NULL,
  `cart_count` int(11) NOT NULL,
  `temp_option` int(11) NOT NULL,
  `size_option` int(11) NOT NULL,
  `cart_price` int(11) NOT NULL,
  PRIMARY KEY (`cart_num`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- 테이블 데이터 cafe_manager.cart:~6 rows (대략적) 내보내기
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT IGNORE INTO `cart` (`cart_num`, `table_id`, `menu_name`, `cart_count`, `temp_option`, `size_option`, `cart_price`) VALUES
	(1, 1, '아메리카노', 2, 1, 3, 5000),
	(2, 7, '카페라떼', 1, 0, 1, 4000),
	(3, 1, '아메리카노', 2, 1, 3, 5000),
	(4, 7, '카페라떼', 1, 0, 1, 4000),
	(5, 1, '아메리카노', 2, 1, 3, 5000),
	(6, 7, '카페라떼', 1, 0, 1, 4000);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;

-- 테이블 cafe_manager.employee 구조 내보내기
CREATE TABLE IF NOT EXISTS `employee` (
  `emp_id` int(11) NOT NULL,
  `emp_name` varchar(20) NOT NULL,
  PRIMARY KEY (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 cafe_manager.employee:~4 rows (대략적) 내보내기
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT IGNORE INTO `employee` (`emp_id`, `emp_name`) VALUES
	(1111, 'cafe_manager'),
	(2222, 'cafe_staff'),
	(3333, 'cafe_staff'),
	(4444, 'cafe_staff');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;

-- 테이블 cafe_manager.macaron_frozen 구조 내보내기
CREATE TABLE IF NOT EXISTS `macaron_frozen` (
  `storage_id` int(11) NOT NULL,
  `mac_name` varchar(20) NOT NULL,
  `mac_count` int(11) NOT NULL,
  `mac_date` date DEFAULT NULL,
  `mac_shelf` date DEFAULT NULL,
  `mac_expire` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 cafe_manager.macaron_frozen:~6 rows (대략적) 내보내기
/*!40000 ALTER TABLE `macaron_frozen` DISABLE KEYS */;
INSERT IGNORE INTO `macaron_frozen` (`storage_id`, `mac_name`, `mac_count`, `mac_date`, `mac_shelf`, `mac_expire`) VALUES
	(1, '딸기 마카롱', 10, '2021-04-21', '2021-04-28', 7),
	(1, '포도 마카롱', 10, '2021-04-21', '2021-04-24', 3),
	(1, '레몬 마카롱', 10, '2021-04-21', '2021-04-28', 7),
	(1, '딸기 마카롱', 10, '2021-04-21', '2021-04-28', 7),
	(1, '포도 마카롱', 10, '2021-04-21', '2021-04-24', 3),
	(1, '레몬 마카롱', 10, '2021-04-21', '2021-04-28', 7);
/*!40000 ALTER TABLE `macaron_frozen` ENABLE KEYS */;

-- 테이블 cafe_manager.menu 구조 내보내기
CREATE TABLE IF NOT EXISTS `menu` (
  `menu_id` int(5) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(20) NOT NULL,
  `cate_id` int(5) NOT NULL,
  `price` int(10) NOT NULL,
  `menu_state` int(2) NOT NULL,
  `menu_title` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`menu_id`,`menu_name`),
  KEY `cate_id_fk` (`cate_id`),
  CONSTRAINT `cate_id_fk` FOREIGN KEY (`cate_id`) REFERENCES `menu_cate` (`cate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- 테이블 데이터 cafe_manager.menu:~15 rows (대략적) 내보내기
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT IGNORE INTO `menu` (`menu_id`, `menu_name`, `cate_id`, `price`, `menu_state`, `menu_title`) VALUES
	(1, '아메리카노', 1, 3900, 1, 'BEST'),
	(2, '카페라떼', 1, 4000, 1, NULL),
	(3, '카페모카', 1, 4100, 1, NULL),
	(4, '레드벨벳', 2, 3900, 1, 'BEST'),
	(5, '뉴욕치즈케이크', 2, 3900, 1, NULL),
	(6, '생크림케이크', 2, 3900, 1, NULL),
	(7, '딸기마카롱', 3, 2000, 1, NULL),
	(8, '바나나마카롱', 3, 2200, 1, NULL),
	(9, '얼그레이마카롱', 3, 2300, 1, 'BEST'),
	(10, '딸기쥬스', 4, 3900, 1, NULL),
	(11, '블루베리쥬스', 4, 3900, 1, NULL),
	(12, '망고쥬스', 4, 3900, 1, 'BEST'),
	(13, '딸기스무디', 5, 3900, 1, NULL),
	(14, '블루베리스무드', 5, 3900, 1, NULL),
	(15, '망고스무디', 5, 3900, 1, 'BEST');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;

-- 테이블 cafe_manager.menu_cate 구조 내보내기
CREATE TABLE IF NOT EXISTS `menu_cate` (
  `cate_id` int(5) NOT NULL,
  `cate_name` varchar(20) NOT NULL,
  PRIMARY KEY (`cate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 cafe_manager.menu_cate:~5 rows (대략적) 내보내기
/*!40000 ALTER TABLE `menu_cate` DISABLE KEYS */;
INSERT IGNORE INTO `menu_cate` (`cate_id`, `cate_name`) VALUES
	(1, 'Coffee'),
	(2, 'Bread & Cake'),
	(3, 'Dessert'),
	(4, 'Juice'),
	(5, 'Smoothie');
/*!40000 ALTER TABLE `menu_cate` ENABLE KEYS */;

-- 테이블 cafe_manager.order_detail 구조 내보내기
CREATE TABLE IF NOT EXISTS `order_detail` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `cart_id` int(11) NOT NULL,
  `table_id` int(11) NOT NULL,
  `menu_name2` varchar(20) NOT NULL,
  `emp_id` int(11) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `order_detail_cart_fk` (`cart_id`),
  CONSTRAINT `order_detail_cart_fk` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_num`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- 테이블 데이터 cafe_manager.order_detail:~3 rows (대략적) 내보내기
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT IGNORE INTO `order_detail` (`order_id`, `cart_id`, `table_id`, `menu_name2`, `emp_id`) VALUES
	(1, 1, 1, '아메리카노', 1),
	(2, 1, 1, '아메리카노', 2),
	(3, 2, 1, '카페라뗴', 1);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;

-- 테이블 cafe_manager.recipe 구조 내보내기
CREATE TABLE IF NOT EXISTS `recipe` (
  `recipe_num` int(11) NOT NULL AUTO_INCREMENT,
  `recipe_menu` varchar(20) NOT NULL,
  `item1_name` varchar(20) NOT NULL,
  `item1_count` int(11) NOT NULL,
  `item2_name` varchar(20) DEFAULT NULL,
  `item2_count` int(11) DEFAULT NULL,
  `item3_name` varchar(20) DEFAULT NULL,
  `item3_count` int(11) DEFAULT NULL,
  `item4_name` varchar(20) DEFAULT NULL,
  `item4_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`recipe_num`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- 테이블 데이터 cafe_manager.recipe:~7 rows (대략적) 내보내기
/*!40000 ALTER TABLE `recipe` DISABLE KEYS */;
INSERT IGNORE INTO `recipe` (`recipe_num`, `recipe_menu`, `item1_name`, `item1_count`, `item2_name`, `item2_count`, `item3_name`, `item3_count`, `item4_name`, `item4_count`) VALUES
	(1, '아메리카노', '원두', 15, NULL, NULL, NULL, NULL, NULL, NULL),
	(2, '카페라떼', '원두', 15, '우유', 200, NULL, NULL, NULL, NULL),
	(3, '딸기스무디', '딸기', 200, '우유', 50, '요구르트', 15, NULL, NULL),
	(4, '망고스무디', '망고', 250, '우유', 50, '요구르트', 15, NULL, NULL),
	(5, '블루베리스무디', '블루베리', 300, '우유', 50, '요구르트', 10, NULL, NULL),
	(6, '딸기주스', '딸기', 200, NULL, NULL, NULL, NULL, NULL, NULL),
	(7, '망고주스', '망고', 250, NULL, NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `recipe` ENABLE KEYS */;

-- 테이블 cafe_manager.stock_bean 구조 내보내기
CREATE TABLE IF NOT EXISTS `stock_bean` (
  `storage_id` int(11) NOT NULL,
  `bean_name` varchar(20) NOT NULL,
  `bean_count` int(11) NOT NULL,
  `bean_date` date DEFAULT NULL,
  `bean_shelf` date DEFAULT NULL,
  `bean_expire` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 cafe_manager.stock_bean:~2 rows (대략적) 내보내기
/*!40000 ALTER TABLE `stock_bean` DISABLE KEYS */;
INSERT IGNORE INTO `stock_bean` (`storage_id`, `bean_name`, `bean_count`, `bean_date`, `bean_shelf`, `bean_expire`) VALUES
	(2, '에티오피', 10, '2021-04-21', '2021-04-28', 7),
	(2, '콜롬비아', 10, '2021-04-21', '2021-04-26', 5);
/*!40000 ALTER TABLE `stock_bean` ENABLE KEYS */;

-- 테이블 cafe_manager.stock_dairy 구조 내보내기
CREATE TABLE IF NOT EXISTS `stock_dairy` (
  `storage_id` int(11) NOT NULL,
  `dairy_name` varchar(20) NOT NULL,
  `dairy_count` int(11) NOT NULL,
  `dairy_date` date DEFAULT NULL,
  `dairy_shelf` date DEFAULT NULL,
  `dairy_expire` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 cafe_manager.stock_dairy:~3 rows (대략적) 내보내기
/*!40000 ALTER TABLE `stock_dairy` DISABLE KEYS */;
INSERT IGNORE INTO `stock_dairy` (`storage_id`, `dairy_name`, `dairy_count`, `dairy_date`, `dairy_shelf`, `dairy_expire`) VALUES
	(5, '우유', 15, '2021-04-21', '2021-04-28', 7),
	(5, '생크림', 15, '2021-04-21', '2021-04-24', 3),
	(5, '요구르트', 13, '2021-04-21', '2021-04-27', 6);
/*!40000 ALTER TABLE `stock_dairy` ENABLE KEYS */;

-- 테이블 cafe_manager.stock_dessert 구조 내보내기
CREATE TABLE IF NOT EXISTS `stock_dessert` (
  `storage_id` int(11) NOT NULL,
  `dessert_name` varchar(20) NOT NULL,
  `dessert_count` int(11) NOT NULL,
  `dessert_date` date DEFAULT NULL,
  `dessert_shelf` date DEFAULT NULL,
  `dessert_expire` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 cafe_manager.stock_dessert:~8 rows (대략적) 내보내기
/*!40000 ALTER TABLE `stock_dessert` DISABLE KEYS */;
INSERT IGNORE INTO `stock_dessert` (`storage_id`, `dessert_name`, `dessert_count`, `dessert_date`, `dessert_shelf`, `dessert_expire`) VALUES
	(4, '치즈번', 3, '2021-04-21', '2021-04-24', 3),
	(4, '모카번', 5, '2021-04-21', '2021-04-26', 5),
	(4, '허니버터브레드', 7, '2021-04-21', '2021-04-25', 4),
	(4, '레드벨벳', 5, '2021-04-21', '2021-04-27', 6),
	(4, '뉴욕치즈케이크', 5, '2021-04-21', '2021-04-26', 5),
	(4, '생크림케이크', 3, '2021-04-21', '2021-04-24', 3),
	(4, '막대과자', 10, '2021-04-21', '2021-04-22', 1),
	(4, '초콜렛', 5, '2021-04-21', '2021-04-22', 1);
/*!40000 ALTER TABLE `stock_dessert` ENABLE KEYS */;

-- 테이블 cafe_manager.stock_fruit 구조 내보내기
CREATE TABLE IF NOT EXISTS `stock_fruit` (
  `storage_id` int(11) NOT NULL,
  `fruit_name` varchar(20) NOT NULL,
  `fruit_count` int(11) NOT NULL,
  `fruit_date` date DEFAULT NULL,
  `fruit_shelf` date DEFAULT NULL,
  `fruit_expire` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 cafe_manager.stock_fruit:~4 rows (대략적) 내보내기
/*!40000 ALTER TABLE `stock_fruit` DISABLE KEYS */;
INSERT IGNORE INTO `stock_fruit` (`storage_id`, `fruit_name`, `fruit_count`, `fruit_date`, `fruit_shelf`, `fruit_expire`) VALUES
	(3, '블루베리', 10, '2021-04-21', '2021-04-24', 3),
	(3, '딸기', 15, '2021-04-21', '2021-04-26', 5),
	(3, '청포도', 7, '2021-04-21', '2021-04-26', 5),
	(3, '망고', 25, '2021-04-21', '2021-04-28', 7);
/*!40000 ALTER TABLE `stock_fruit` ENABLE KEYS */;

-- 테이블 cafe_manager.storage_cafe 구조 내보내기
CREATE TABLE IF NOT EXISTS `storage_cafe` (
  `storage_id` int(11) NOT NULL AUTO_INCREMENT,
  `storage_name` varchar(20) NOT NULL,
  PRIMARY KEY (`storage_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- 테이블 데이터 cafe_manager.storage_cafe:~5 rows (대략적) 내보내기
/*!40000 ALTER TABLE `storage_cafe` DISABLE KEYS */;
INSERT IGNORE INTO `storage_cafe` (`storage_id`, `storage_name`) VALUES
	(1, '마카롱창고'),
	(2, '원두창고'),
	(3, '과일창고'),
	(4, '디저트창고'),
	(5, '유제품창고');
/*!40000 ALTER TABLE `storage_cafe` ENABLE KEYS */;

-- 테이블 cafe_manager.tab 구조 내보내기
CREATE TABLE IF NOT EXISTS `tab` (
  `tab_num` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`tab_num`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- 테이블 데이터 cafe_manager.tab:~8 rows (대략적) 내보내기
/*!40000 ALTER TABLE `tab` DISABLE KEYS */;
INSERT IGNORE INTO `tab` (`tab_num`) VALUES
	(1),
	(2),
	(3),
	(4),
	(5),
	(6),
	(7),
	(8);
/*!40000 ALTER TABLE `tab` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
