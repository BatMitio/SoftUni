DROP TABLE IF EXISTS `people`;
DROP TABLE IF EXISTS `passports`;

CREATE TABLE `people`(
	`person_id` INT,
    `first_name` VARCHAR(50) NOT NULL,
    `salary` DOUBLE(10, 2),
    `passport_id` INT UNIQUE
);

CREATE TABLE `passports`(
	`passport_id` INT,
    `passport_number` VARCHAR(8) UNIQUE
);

INSERT INTO `people` (`person_id`, `first_name`, `salary`, `passport_id`)
VALUES 
(1, 'Roberto', 43300.00, 102),
(2, 'Tom', 56100.00, 103),
(3, 'Yana', 60200.00, 101);

INSERT INTO `passports` (`passport_id`, `passport_number`)
VALUES 
(101, 'N34FG21B'),
(102, 'K65LO4R7'),
(103, 'ZE657QP2');

ALTER TABLE `passports`
MODIFY `passport_id` INT PRIMARY KEY;

ALTER TABLE `people`
MODIFY `person_id` INT PRIMARY KEY,
ADD CONSTRAINT fk_people_passports
FOREIGN KEY `people`(`passport_id`)
REFERENCES `passports`(`passport_id`);


/* 2. One-to-Many Relationship */
DROP TABLE IF EXISTS `models`;
DROP TABLE IF EXISTS `manufacturers`;

CREATE TABLE `manufacturers`(
	`manufacturer_id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `established_on` DATE NOT NULL
);

CREATE TABLE `models`(
	`model_id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `manufacturer_id` INT NOT NULL
);

INSERT INTO `manufacturers`
VALUES
(1, 'BMW', '1916/03/01'),
(2, 'Tesla', '2003/01/01'),
(3, 'Lada', '1966/05/01');

INSERT INTO `models`
VALUES
(101, 'X1', 1),
(102, 'i6', 1),
(103, 'Model S', 2),
(104, 'Model X', 2),
(105, 'Model 3', 2),
(106, 'Nova', 3);	

ALTER TABLE `models`
ADD CONSTRAINT fk_models_manufacturers
FOREIGN KEY `models`(`manufacturer_id`)
REFERENCES `manufacturers`(`manufacturer_id`);

/* 3. Many-To-Many Relationship */
DROP TABLE IF EXISTS `students_exams`;
DROP TABLE IF EXISTS `students`;
DROP TABLE IF EXISTS `exams`;

CREATE TABLE `students`(
	`student_id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL
);

CREATE TABLE `exams`(
	`exam_id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL
);

CREATE TABLE `students_exams`(
	`student_id` INT NOT NULL,
    `exam_id` INT NOT NULL	
);

INSERT INTO `students`
VALUES
(1, 'Mila'),
(2, 'Toni'),
(3, 'Ron');

INSERT INTO `exams`
VALUES
(101, 'Spring MVC'),
(102, 'Neo4j'),
(103, 'Oracle 11g');

INSERT INTO `students_exams`
VALUES
(1, 101),
(1, 102),
(2, 101),
(3, 103),
(2, 102),
(2, 103);

ALTER TABLE `students_exams`
ADD CONSTRAINT pk_students_exams
PRIMARY KEY (`student_id`, `exam_id`),
ADD CONSTRAINT fk_students_exams_students
FOREIGN KEY `students_exams`(`student_id`)
REFERENCES `students`(`student_id`),
ADD CONSTRAINT fk_students_exams_exams
FOREIGN KEY `students`(`exam_id`)
REFERENCES `exams`(`exam_id`);


/* 4. Self-Referencing */

DROP TABLE IF EXISTS `teachers`;

CREATE TABLE `teachers`(
	`teacher_id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `manager_id` INT
);

INSERT INTO `teachers`
VALUES
(101, 'John', NULL),
(102, 'Maya', 106),
(103, 'Silvia', 106),
(104, 'Ted', 105),
(105, 'Mark', 101),
(106, 'Greta', 101);

ALTER TABLE `teachers`
ADD CONSTRAINT fk_teachers_teachers
FOREIGN KEY `teachers`(`manager_id`)
REFERENCES `teachers`(`teacher_id`);

/* 5. Online Store Database */
DROP TABLE IF EXISTS `order_items`;
DROP TABLE IF EXISTS `items`;
DROP TABLE IF EXISTS `item_types`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `customers`;
DROP TABLE IF EXISTS `cities`;

CREATE TABLE `item_types`(
	`item_type_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50)
);

CREATE TABLE `items`(
	`item_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50),
    `item_type_id` INT(11),
    CONSTRAINT fk_items_item_types
    FOREIGN KEY `items`(`item_type_id`)
    REFERENCES `item_types`(`item_type_id`)
);

CREATE TABLE `cities`(
	`city_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50)
);

CREATE TABLE `customers`(
	`customer_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50),
    `birthday` DATE,
    `city_id` INT(11),
    CONSTRAINT fk_customers_cities
    FOREIGN KEY `customers`(`city_id`)
    REFERENCES `cities`(`city_id`)
);

CREATE TABLE `orders`(
	`order_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
    `customer_id` INT(11),
    CONSTRAINT fk_orders_customers
    FOREIGN KEY `orders`(`customer_id`)
    REFERENCES `customers`(`customer_id`)
);

CREATE TABLE `order_items`(
	`order_id` INT(11),
    `item_id` INT(11),
    CONSTRAINT fk_order_items_items
    FOREIGN KEY `order_items`(`item_id`)
    REFERENCES `items`(`item_id`),
    CONSTRAINT fk_order_items_orders
    FOREIGN KEY `order_items`(`order_id`)
    REFERENCES `orders`(`order_id`)
);

/* 6. University Database */

DROP TABLE IF EXISTS `payments`;
DROP TABLE IF EXISTS `agenda`;
DROP TABLE IF EXISTS `subjects`;
DROP TABLE IF EXISTS `students`;
DROP TABLE IF EXISTS `majors`;

CREATE TABLE `subjects`(
	`subject_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
    `subject_name` VARCHAR(50)
);

CREATE TABLE `majors`(
	`major_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50)
);

CREATE TABLE `students`(
	`student_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
    `student_number` VARCHAR(12),
    `student_name` VARCHAR(50),
    `major_id` INT(11),
    CONSTRAINT fk_students_majors
    FOREIGN KEY `students`(`major_id`)
    REFERENCES `majors`(`major_id`)
);

CREATE TABLE `payments`(
	`payment_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
    `payment_date` DATE,
    `payment_amount` DECIMAL(8, 2),
    `student_id` INT(11),
    CONSTRAINT fk_payments_students
    FOREIGN KEY `payments`(`student_id`)
    REFERENCES `students`(`student_id`)
);

CREATE TABLE `agenda`(
	`student_id` INT(11),
    `subject_id` INT(11),
    CONSTRAINT fk_agenda_students
    FOREIGN KEY `agenda`(`student_id`)
    REFERENCES `students`(`student_id`),
    CONSTRAINT fk_agenda_subjects
    FOREIGN KEY `agenda`(`subject_id`)
    REFERENCES `subjects`(`subject_id`)
);


/* 9. Peaks in Rila */

SELECT m.mountain_range AS `mountain_range`, p.peak_name AS `peak_name`, p.elevation AS `peak_elevation`
FROM `peaks` AS p 
JOIN `mountains` AS m
ON p.mountain_id = m.id
WHERE m.mountain_range = 'Rila'
ORDER BY `peak_elevation` DESC;