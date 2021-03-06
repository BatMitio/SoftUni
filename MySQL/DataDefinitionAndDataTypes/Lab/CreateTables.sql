USE gamebar;

DROP TABLE IF EXISTS employees;
CREATE TABLE employees(
	id 			INT 		PRIMARY KEY AUTO_INCREMENT,
    first_name 	VARCHAR(45) NOT NULL,
    last_name  	VARCHAR(45) NOT NULL
);

DROP TABLE IF EXISTS categories;
CREATE TABLE categories(
	id 			INT 		PRIMARY KEY AUTO_INCREMENT,
    name 		VARCHAR(45) NOT NULL
);

DROP TABLE IF EXISTS products;
CREATE TABLE products(
	id 			INT 		PRIMARY KEY AUTO_INCREMENT,
    name 		VARCHAR(45) NOT NULL,
    category_id INT 		NOT NULL
);