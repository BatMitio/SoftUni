DROP TABLE IF EXISTS `mountains`;
CREATE TABLE `mountains` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS `peaks`;
CREATE TABLE `peaks` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `mountain_id` INT NOT NULL,
    CONSTRAINT fk_peaks_mountains
    FOREIGN KEY (`mountain_id`)
    REFERENCES `mountains`(`id`)
);


SELECT v.`driver_id`, v.`vehicle_type`, concat_ws(' ', c.`first_name`, c.`last_name`) AS `driver_name`
FROM `vehicles` AS v
JOIN `campers` AS c
ON v.`driver_id` = c.`id`;


SELECT 
r.`starting_point` AS `route_starting_point`, 
r.`end_point` AS `route_ending_point`, 
r.`leader_id` AS `leader_id`, 
concat_ws(' ', c.`first_name`, c.`last_name`) AS `leader_name`
FROM `routes` AS r
JOIN `campers` AS c
ON r.`leader_id` = c.`id`;




DROP TABLE IF EXISTS `peaks`;
DROP TABLE IF EXISTS `mountains`;

CREATE TABLE `mountains` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL
);

CREATE TABLE `peaks` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `mountain_id` INT NOT NULL,
    CONSTRAINT fk_peaks_mountains
    FOREIGN KEY (`mountain_id`)
    REFERENCES `mountains`(`id`)
    ON DELETE CASCADE
);


