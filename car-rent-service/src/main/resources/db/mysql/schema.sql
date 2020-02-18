CREATE DATABASE IF NOT EXISTS rentcar;

ALTER DATABASE rentcar
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

GRANT ALL PRIVILEGES ON rentcar.* TO pc@localhost IDENTIFIED BY 'pc';

USE rentcar;

CREATE TABLE IF NOT EXISTS category (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(30),
  INDEX(name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS car (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  manufacturer VARCHAR(80),
  model_name VARCHAR(200) NOT NULL,
  model_year INT(4) NOT NULL,
  category_id INT(4) UNSIGNED NOT NULL,
  weekday_price INT(10) NOT NULL,
  weekend_price INT(10) NOT NULL,
  weekday_loyalty_price INT(10) NOT NULL,
  weekend_loyalty_price INT(10) NOT NULL,
  FOREIGN KEY (category_id) REFERENCES category(id),
  INDEX(manufacturer)
) engine=InnoDB;

