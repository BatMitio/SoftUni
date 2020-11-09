CREATE SCHEMA `fsd`;

USE `fsd`;

-- 1. Table Design --
CREATE TABLE `countries` (
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL
);

CREATE TABLE `towns` (
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`country_id` INT NOT NULL,
CONSTRAINT fk_towns_countries
FOREIGN KEY `towns` (`country_id`)
REFERENCES `countries` (`id`)
);

CREATE TABLE `stadiums` (
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`capacity` INT NOT NULL,
`town_id` INT NOT NULL,
CONSTRAINT fk_stadiums_towns
FOREIGN KEY `stadiums` (`town_id`)
REFERENCES `towns` (`id`)
);

CREATE TABLE `teams` (
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`established` DATE NOT NULL,
`fan_base` BIGINT NOT NULL DEFAULT 0,
`stadium_id` INT NOT NULL,
CONSTRAINT fk_teams_stadiums
FOREIGN KEY `teams` (`stadium_id`)
REFERENCES `stadiums` (`id`)
);

CREATE TABLE `skills_data` (
`id` INT PRIMARY KEY AUTO_INCREMENT,
`dribbling` INT DEFAULT 0,
`pace` INT DEFAULT 0,
`passing` INT DEFAULT 0,
`shooting` INT DEFAULT 0,
`speed` INT DEFAULT 0,
`strength` INT DEFAULT 0
);

CREATE TABLE `players` (
`id` INT PRIMARY KEY AUTO_INCREMENT,
`first_name` VARCHAR(10) NOT NULL,
`last_name` VARCHAR(20) NOT NULL,
`age` INT NOT NULL DEFAULT 0,
`position` CHAR(1) NOT NULL,
`salary` DECIMAL(10, 2) NOT NULL DEFAULT 0,
`hire_date` DATETIME,
`skills_data_id` INT NOT NULL,
`team_id` INT,
CHECK (`position` IN ('A', 'M', 'D')),
CONSTRAINT fk_players_skills_data
FOREIGN KEY `players` (`skills_data_id`)
REFERENCES `skills_data` (`id`),
CONSTRAINT fk_players_teams
FOREIGN KEY `players` (`team_id`)
REFERENCES `teams` (`id`)
);

CREATE TABLE `coaches` (
`id` INT PRIMARY KEY AUTO_INCREMENT,
`first_name` VARCHAR(10) NOT NULL,
`last_name` VARCHAR(20) NOT NULL,
`salary` DECIMAL(10, 2) NOT NULL DEFAULT 0,
`coach_level` INT NOT NULL DEFAULT 0
);

CREATE TABLE `players_coaches` (
`player_id` INT,
`coach_id` INT,
CONSTRAINT pk_players_coaches
PRIMARY KEY `players_coaches` (`player_id`, `coach_id`),
CONSTRAINT fk_players_coaches_players
FOREIGN KEY `players_coaches` (`player_id`)
REFERENCES `players` (`id`),
CONSTRAINT fk_players_coaches_coaches
FOREIGN KEY `players_coaches` (`coach_id`)
REFERENCES `coaches` (`id`)
);

-- 2. Insert --
INSERT INTO `coaches` (`first_name`, `last_name`, `salary`, `coach_level`)
SELECT `first_name`, `last_name`, `salary` * 2, CHAR_LENGTH(`first_name`)
FROM `players`
WHERE `age` >= 45;

-- 3. Update --
SET SQL_SAFE_UPDATES = 0;

UPDATE `coaches` AS c
SET c.`coach_level` = c.`coach_level` + 1
WHERE c.`id` IN (SELECT pc.`coach_id` FROM `players_coaches` AS pc) AND LEFT(c.`first_name`, 1) = 'A';

-- 4. Delete --
DELETE FROM `players`
WHERE `age` >= 45;

-- 5. Players --
SELECT `first_name`, `age`, `salary`
FROM `players`
ORDER BY `salary` DESC;

-- 6. Young offense players without contract
SELECT p.`id`, CONCAT_WS(' ', p.`first_name`, p.`last_name`) AS `full_name`,
p.`age`, p.`position`, p.`hire_date`
FROM `players` AS p
INNER JOIN `skills_data` AS sd
ON p.`skills_data_id` = sd.`id`
WHERE p.`age` < 23 AND p.`position` = 'A' AND p.`hire_date` IS NULL AND sd.`strength` > 50
ORDER BY p.`salary` ASC, p.`age` ASC;

-- 7. Detail info for all teams --
SELECT t.`name` AS `team_name`, t.`established`, t.`fan_base`, COUNT(p.`id`) AS `count_of_players`
FROM `teams` AS t
LEFT JOIN `players` AS p
ON t.`id` = p.`team_id`
GROUP BY t.`id`
ORDER BY `count_of_players` DESC, t.`fan_base` DESC;

-- 8. The fastest player by towns --
SELECT MAX(sd.`speed`) AS `max_speed`, t1.`name` AS `town_name`
FROM `players` AS p
RIGHT JOIN `skills_data` AS sd
ON p.`skills_data_id` = sd.`id`
RIGHT JOIN `teams` AS t
ON p.`team_id` = t.`id`
RIGHT JOIN `stadiums` AS s
ON t.`stadium_id` = s.`id`
RIGHT JOIN `towns` AS t1
ON s.`town_id` = t1.`id`
WHERE t.`name` != 'Devify'
GROUP BY t1.`name`
ORDER BY `max_speed` DESC, t1.`name` ASC;

-- 9. Total salaries and players by country --
SELECT c.`name`, COUNT(p.`id`) AS `total_count_of_players`, SUM(p.`salary`) AS `total_sum_of_salaries`
FROM `players` AS p
RIGHT JOIN `teams` AS t
ON p.`team_id` = t.`id`
RIGHT JOIN `stadiums` AS s
ON t.`stadium_id` = s.`id`
RIGHT JOIN `towns` AS t1
ON s.`town_id` = t1.`id`
RIGHT JOIN `countries` AS c
ON t1.`country_id` = c.`id`
GROUP BY c.`name`
ORDER BY `total_count_of_players` DESC, c.`name` ASC;

-- 10. Find all players that play on stadium --
DELIMITER //
CREATE FUNCTION udf_stadium_players_count(stadium_name VARCHAR(30))
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE players_count INT;
    SET players_count := (
    SELECT COUNT(p.`id`)
    FROM `players` AS p
    INNER JOIN `teams` AS t
    ON p.`team_id` = t.`id`
    INNER JOIN `stadiums` AS s
    ON t.`stadium_id` = s.`id`
    WHERE s.`name` = stadium_name
    );
    RETURN players_count;
END//
DELIMITER ;

SELECT udf_stadium_players_count ('Jaxworks') as `count`;
SELECT udf_stadium_players_count ('Linklinks') as `count`; 

-- 11. Find good playmaker by teams --
DELIMITER //
CREATE PROCEDURE udp_find_playmaker(min_dribble_points INT, team_name VARCHAR(45))
BEGIN
	SELECT CONCAT_WS(' ', p.`first_name`, p.`last_name`) AS `full_name`,
    p.`age`, p.`salary`, sd.`dribbling`, sd.`speed`, t.`name` AS `town_name`
    FROM `players` AS p
    INNER JOIN `skills_data` AS sd
    ON p.`skills_data_id` = sd.`id`
    INNER JOIN `teams` AS t
    ON p.`team_id` = t.`id`
    WHERE sd.`dribbling` > min_dribble_points
    AND
    sd.`speed` > (SELECT AVG(`speed`) FROM `skills_data`)
    AND
    t.`name` = team_name
    ORDER BY sd.`speed` DESC
    LIMIT 1;
END//
DELIMITER ;
	
CALL udp_find_playmaker(20, 'Skyble');