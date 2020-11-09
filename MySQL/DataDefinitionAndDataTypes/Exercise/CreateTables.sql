


DROP TABLE IF EXISTS minions;
CREATE TABLE minions(
	id 		INT 			AUTO_INCREMENT,
    name	VARCHAR(50) 	NOT NULL,
	age		INT,
    CONSTRAINT PRIMARY KEY (id)
);

DROP TABLE IF EXISTS towns;
CREATE TABLE towns(
	id INT 			AUTO_INCREMENT,
    name	VARCHAR(50)		NOT NULL,
	CONSTRAINT PRIMARY KEY(id)
);

ALTER TABLE minions
ADD town_id INT NOT NULL,
ADD CONSTRAINT fk_town FOREIGN KEY (town_id) REFERENCES towns(id);

INSERT INTO towns (name)
VALUES
	('Sofia'),
	('Plovdiv'),
	('Varna');
    
INSERT INTO minions (name, age, town_id)
VALUES
('Kevin', 22, 1),
('Bob', 15, 3),
('Steward', NULL, 2);

TRUNCATE TABLE minions;

DROP TABLE minions;
DROP TABLE towns;

/* 6. Create Table People */
DROP TABLE IF EXISTS people;
CREATE TABLE people(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    picture MEDIUMBLOB,
    height FLOAT(2),
    weight FLOAT(2),
    gender CHARACTER NOT NULL CHECK (gender = 'm' OR gender = 'f'),
    birthdate DATE NOT NULL,
    biography LONGTEXT
);

INSERT INTO people(name, picture, height, weight, gender, birthdate, biography)
VALUES
('abv', NULL, 1.78, 80.3, 'm', '2002-5-06', NULL),
('gde', NULL, 1.70, 80.3, 'f', '2002-5-12', NULL),
('jzi', NULL, 1.00, 80.3, 'm', '2005-5-13', NULL),
('jkl', NULL, 1, 80.3, 'm', '2002-5-06', NULL),
('mno', NULL, 1.7, 80.3, 'f', '2002-5-06', NULL);

/* 7. Create Table Users */
DROP TABLE IF EXISTS users;
CREATE TABLE users(
	id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL,
    password VARCHAR(26) NOT NULL,
    profile_picture MEDIUMBLOB,
    last_login_time TIMESTAMP,
    is_deleted VARCHAR(5),
    CONSTRAINT  is_deleted_check CHECK (is_deleted = 'true' OR is_deleted= 'false')
);

INSERT INTO users(username, password, profile_picture, last_login_time, is_deleted)
VALUES
('abv', 'gde', NULL, CURRENT_TIMESTAMP(), 'true'),
('jzi', 'jkl', NULL, NULL, 'false'),
('mno', 'prs', NULL, NULL, 'false'),
('tuf', 'hcs', NULL, NULL, 'true'),
('hsh', 'tyx', NULL, NULL, 'true');

/* 8. Change Primary Key */
ALTER TABLE users 
MODIFY id INT NOT NULL,
DROP PRIMARY KEY,
ADD CONSTRAINT pk_users PRIMARY KEY(id, username);

/* 9. Set Default Value Of A Field */
ALTER TABLE users
CHANGE COLUMN last_login_time last_login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

/* 10. Set Unique Field */
ALTER TABLE users
DROP PRIMARY KEY,
ADD CONSTRAINT pk_users PRIMARY KEY (id),
ADD CONSTRAINT unique_username UNIQUE (username);

/* 11. Movies Database */
DROP DATABASE IF EXISTS Movies;
CREATE DATABASE Movies;
USE Movies;

DROP TABLE IF EXISTS directors;
CREATE TABLE directors(
	id INT PRIMARY KEY AUTO_INCREMENT,
    director_name VARCHAR(50) NOT NULL,
    notes MEDIUMTEXT
);

DROP TABLE IF EXISTS genres;
CREATE TABLE genres(
	id INT PRIMARY KEY AUTO_INCREMENT,
    genre_name VARCHAR(50) NOT NULL,
    notes MEDIUMTEXT
);

DROP TABLE IF EXISTS categories;
CREATE TABLE categories(
	id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(50) NOT NULL,
    notes MEDIUMTEXT
);

DROP TABLE IF EXISTS movies;
CREATE TABLE movies(
	id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    director_id INT,
    copyright_year YEAR,
    length FLOAT(2),
    genre_id INT,
    category_id INT,
    rating FLOAT(2), 
    notes MEDIUMTEXT
);

INSERT INTO directors(director_name, notes)
VALUES
("GOSHO SEKELETO", "NQMA TAKAVA DYRJAVA"), 
("PESHO SEKELETO", "IMA TAKAVA DYRJAVA"), 
("MISHO SEKELETO", "DEBA TAKAVATA DYRJAVA"), 
("MITKO SEKELETO", "EBAA GO"), 
("PITKO SEKELETO", "KUR ZA LOKOOOOO"); 

INSERT INTO genres(genre_name, notes)
VALUES
("GOSHO JANRA", "NQMA TAKAVA DYRJAVA"), 
("PESHO JANRA", "IMA TAKAVA DYRJAVA"), 
("MISHO JANRA", "DEBA TAKAVATA DYRJAVA"), 
("MITKO JANRA", "EBAA GO"), 
("PITKO JANRA", "KUR ZA LOKOOOOO"); 

INSERT INTO categories(category_name, notes)
VALUES
("GOSHO KATEGORIQ", "NQMA TAKAVA DYRJAVA"), 
("PESHO KATEGORIQ", "IMA TAKAVA DYRJAVA"), 
("MISHO KATEGORIQ", "DEBA TAKAVATA DYRJAVA"), 
("MITKO KATEGORIQ", "EBAA GO"), 
("PITKO KATEGORIQ", "KUR ZA LOKOOOOO"); 

INSERT INTO movies(title, director_id, copyright_year, length, genre_id, category_id, rating, notes)
VALUES
("Nekav film1", 1, current_date, 120, 1, 1, 1, "ne"),
("Nekav film2", 2, current_date, 120, 2, 2, 2, NULL),
("Nekav film3", 3, current_date, 120, 3, 3, 3, "ne"),
("Nekav film4", 4, current_date, 120, 4, 4, 1, "ne"),
("Nekav film5", 5, current_date, 120, 5, 5, 1, "ne");

/* 12. Car Rental Database */
DROP DATABASE IF EXISTS car_rental;
CREATE DATABASE car_rental;
USE car_rental;

DROP TABLE IF EXISTS categories;
CREATE TABLE categories(
	id INT PRIMARY KEY AUTO_INCREMENT,
    category VARCHAR(50),
    daily_rate DOUBLE(4, 2),
    weekly_rate DOUBLE(4, 2),
    monthly_rate DOUBLE(4, 2),
    weekend_rate DOUBLE(4, 2)    
);

DROP TABLE IF EXISTS cars;
CREATE TABLE cars(
	id INT PRIMARY KEY AUTO_INCREMENT,
    plate_number VARCHAR(20) UNIQUE,
    make VARCHAR(50),
    model VARCHAR(50),
    car_year YEAR,
    category_id INT,
    doors INT,
    picture MEDIUMBLOB,
    car_condition VARCHAR(300),
    available BOOLEAN
);

DROP TABLE IF EXISTS employees;
CREATE TABLE employees(
	id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    title VARCHAR(50),
	notes MEDIUMTEXT
);

DROP TABLE IF EXISTS customers;
CREATE TABLE customers(
	id INT PRIMARY KEY AUTO_INCREMENT,
    driver_license_number INT,
    full_name VARCHAR(100),
    address VARCHAR(100),
    city VARCHAR(50),
    zip_code INT,
    notes MEDIUMTEXT
);

DROP TABLE IF EXISTS rental_orders;
CREATE TABLE rental_orders(
	id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id INT,
    customer_id INT,
    car_id INT,
    car_condition VARCHAR(300),
    tank_level INT,
    kilometrage_start INT,
    kilometrage_end INT,
    total_kilometrage INT,
    start_date DATE,
    end_date DATE,
    total_days INT,
    rate_applied DOUBLE(4, 2),
    tax_rate DOUBLE(4, 2),
    order_status VARCHAR(100),
	notes MEDIUMTEXT
);

INSERT INTO `categories`
VALUES
(1, 'van', 2.0, 14.0, 56.0, 25.0),
(2, 'coupe', 2.0, 14.0, 56.0, 25.0),
(3, 'sedan', 2.0, 14.0, 56.0, 25.0);
 
INSERT INTO `cars`
VALUES
(1, 654005, 'Kia', 'DeNiro', 2019, 3, 5, NULL, 'used', TRUE),
(2, 684752, 'Honda', 'Civic', 2018, 3, 5, NULL, 'used', TRUE),
(3, 741852, 'Opel', 'Astra', 1992, 4, 3, NULL, 'scrab', FALSE);
 
INSERT INTO `employees`
VALUES
(1, 'Pesho', 'Peshovski', 'Manager', NULL),
(2, 'Gosho', 'Handsome', 'Cleaner', 'I\'m Gosho, the Handsome one, the eternal bachelor'),
(3, 'Al', 'Bundy', 'Door opener', NULL);
 
INSERT INTO `customers`
VALUES
(1, 741852, 'Pesho Peshovski', 'Center', 'Sofia', 1000, NULL),
(2, 852963, 'Gosho Handsome', 'Downtown', 'Bourgas', 1200, NULL),
(3, 963852, 'Al Bundy', 'Center', 'Varna', 1300, NULL);
 
INSERT INTO `rental_orders`
VALUES
(1, 1, 1, 1, 'used', 50, 0, 8, 8, '2019-12-25', '2019-12-31', 6, 4.2, 65.45, 'passed', NULL),
(2, 1, 1, 1, 'used', 50, 0, 8, 8, '2019-12-25', '2019-12-31', 6, 4.2, 65.45, 'in progress', NULL),
(3, 1, 1, 1, 'used', 50, 0, 8, 8, '2019-12-25', '2019-12-31', 6, 4.2, 65.45, 'passed', NULL);