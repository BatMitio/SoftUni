SELECT COUNT(*) AS `count`
FROM `wizzard_deposits`;


SELECT MAX(`magic_wand_size`) AS `longest_magic_wand`
FROM `wizzard_deposits`;


SELECT `deposit_group`, MAX(`magic_wand_size`) AS `longest_magic_wand`
FROM `wizzard_deposits`
GROUP BY `deposit_group`
ORDER BY `longest_magic_wand`, `deposit_group`;


SELECT `deposit_group`
FROM `wizzard_deposits`
GROUP BY `deposit_group`
ORDER BY AVG(`magic_wand_size`)
LIMIT 1;


SELECT `deposit_group`, SUM(`deposit_amount`) AS `total_sum`
FROM `wizzard_deposits`
GROUP BY `deposit_group`
ORDER BY `total_sum`;


SELECT `deposit_group`, SUM(`deposit_amount`) AS `total_sum`
FROM `wizzard_deposits`
WHERE `magic_wand_creator` = 'Ollivander family'
GROUP BY `deposit_group`
ORDER BY `deposit_group`;


SELECT `deposit_group`, SUM(`deposit_amount`) AS `total_sum`
FROM `wizzard_deposits`
WHERE `magic_wand_creator` = 'Ollivander family'
GROUP BY `deposit_group`
HAVING `total_sum` < 150000
ORDER BY `total_sum` DESC;


SELECT `deposit_group`, `magic_wand_creator`, MIN(`deposit_charge`) AS `min_deposit_charge`
FROM `wizzard_deposits`
GROUP BY `deposit_group`, `magic_wand_creator`
ORDER BY `magic_wand_creator`, `deposit_group`;


SELECT (
	CASE
    WHEN `age` BETWEEN 0 AND 10 THEN '[0-10]'
    WHEN `age` BETWEEN 11 AND 20 THEN '[11-20]'
    WHEN `age` BETWEEN 21 AND 30 THEN '[21-30]'
    WHEN `age` BETWEEN 31 AND 40 THEN '[31-40]'
    WHEN `age` BETWEEN 41 AND 50 THEN '[41-50]'
    WHEN `age` BETWEEN 51 AND 60 THEN '[51-60]'
    ELSE '[61+]'
	END
) AS `age_group`, COUNT(*) AS `wizzard_count`
FROM `wizzard_deposits`
GROUP BY `age_group`
ORDER BY `wizzard_count`;


SELECT DISTINCT LEFT(`first_name`, 1) AS `first_letter`
FROM `wizzard_deposits`
WHERE `deposit_group` = 'Troll Chest'
GROUP BY `first_name`
ORDER BY `first_letter`;


SELECT `deposit_group`, `is_deposit_expired`, AVG(`deposit_interest`)
FROM `wizzard_deposits`
WHERE `deposit_start_date` > '1985/01/01'
GROUP BY `deposit_group`, `is_deposit_expired`
ORDER BY `deposit_group` DESC, `is_deposit_expired` ASC;

SELECT SUM(`main`.`difference`) AS `sum_difference`
FROM(SELECT (`deposit_amount` - (SELECT `deposit_amount` FROM `wizzard_deposits` AS `inner` WHERE `inner`.`id` = `middle`.`id` + 1)) AS `difference`
FROM `wizzard_deposits` AS `middle`) AS `main`;



SELECT `department_id`, MIN(`salary`) AS `minimum_salary`
FROM `employees`
WHERE `hire_date` > '2000/01/01'
GROUP BY `department_id`
HAVING `department_id` IN (2, 5, 7);


DROP TABLE IF EXISTS `new_table`;
CREATE TABLE `new_table` AS
SELECT *
FROM `employees`
WHERE `salary` > 30000;

DELETE FROM `new_table`
WHERE `manager_id` = 42;

UPDATE `new_table`
SET `salary` = `salary` + 5000
WHERE `department_id` = 1;

SELECT `department_id`, AVG(`salary`) AS `avg_salary`
FROM `new_table`
GROUP BY `department_id`
ORDER BY `department_id`;

SELECT `department_id`, MAX(`salary`) AS `max_salary`
FROM `employees`
GROUP BY `department_id`
HAVING `max_salary` NOT BETWEEN 30000 AND 70000
ORDER BY `department_id`;


SELECT COUNT(*) AS ``
FROM `employees`
GROUP BY `manager_id`
HAVING `manager_id` IS NULL;


SELECT `department_id`, SUM(`salary`) AS `total_salary`
FROM `employees`
GROUP BY `department_id`
ORDER BY `department_id`;