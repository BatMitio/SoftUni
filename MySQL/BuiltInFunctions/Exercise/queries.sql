SELECT `first_name`, `last_name`
FROM `employees`
WHERE LOWER(LEFT(`first_name`, 2)) = 'sa';

SELECT `first_name`, `last_name`
FROM `employees`
WHERE LOCATE('ei', LOWER(`last_name`)) != 0;

SELECT `first_name`
FROM `employees`
WHERE `department_id` IN (3, 10) AND (EXTRACT(YEAR FROM `hire_date`) BETWEEN 1995 AND 2005)
ORDER BY `employee_id`;

SELECT `first_name`, `last_name`
FROM `employees`
WHERE LOCATE('engineer', LOWER(`job_title`)) = 0
ORDER BY `employee_id`;

SELECT `name`
FROM `towns`
WHERE CHAR_LENGTH(`name`) IN (5, 6)
ORDER BY `name`;

SELECT `town_id`, `name`
FROM `towns`
WHERE LOWER(LEFT(`name`, 1)) IN ('m', 'k', 'b', 'e')
ORDER BY `name`;

SELECT `town_id`, `name`
FROM `towns`
WHERE LOWER(LEFT(`name`, 1)) NOT IN ('r', 'b', 'd')
ORDER BY `name`;



DROP VIEW IF EXISTS `v_employees_hired_after_2000`;
CREATE VIEW `v_employees_hired_after_2000` AS
	SELECT `first_name`, `last_name`
    FROM `employees`
    WHERE EXTRACT(YEAR FROM `hire_date`) > 2000;
    
SELECT * FROM `v_employees_hired_after_2000`;



SELECT `first_name`, `last_name`
FROM `employees`
WHERE CHAR_LENGTH(`last_name`) = 5;

SELECT `country_name`, `iso_code`
FROM `countries`
WHERE `country_name` LIKE "%A%A%A%"
ORDER BY `iso_code`;


SELECT p.`peak_name`, r.`river_name`, CONCAT(LOWER(p.`peak_name`), SUBSTRING(LOWER(r.`river_name`), 2)) AS mix
FROM `peaks` AS p, `rivers` AS r
WHERE RIGHT(p.`peak_name`, 1) = LEFT(r.`river_name`, 1)
ORDER BY mix;


SELECT `name`, DATE_FORMAT(`start`, '%Y-%m-%d') AS 'start'
FROM `games`
WHERE EXTRACT(YEAR FROM `start`) IN (2011, 2012)
ORDER BY `start`, `name`
LIMIT 50;


SELECT `user_name`, SUBSTRING(`email`, LOCATE('@', `email`) + 1) AS 'Email Provider'
FROM `users`
ORDER BY `Email Provider` ASC, `user_name`;


SELECT `user_name`, `ip_address`
FROM `users`
WHERE `ip_address` LIKE '___.1%.%.___'
ORDER BY `user_name`;


SELECT `name`, IF(EXTRACT(HOUR FROM `start`) >= 0 AND EXTRACT(HOUR FROM `start`) < 12, 'Morning', 
IF(EXTRACT(HOUR FROM `start`) >= 12 AND EXTRACT(HOUR FROM `start`) < 18, 'Afternoon', 'Evening')) AS 'Part of the Day',
IF(`duration` <= 3, 'Extra Short', IF(`duration` > 3 AND `duration` <= 6, 'Short', IF(`duration` > 6 AND `duration` <= 10, 'Long', 'Extra Long'))) AS 'Duration'
FROM `games`;


SELECT `product_name`, `order_date`, DATE_ADD(`order_date`, INTERVAL 3 DAY) AS 'pay_due', DATE_ADD(`order_date`, INTERVAL 1 MONTH) AS 'deliver_due'
FROM `orders`;