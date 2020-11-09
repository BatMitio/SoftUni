/* 1. Managers */

SELECT DISTINCT dep.`manager_id` AS `employee_id`, 
concat_ws(' ', emp.`first_name`, emp.`last_name`) AS `full_name`, 
dep.`department_id` AS `department_id`,
dep.`name`
FROM `departments` dep
INNER JOIN `employees` emp
ON dep.`manager_id` = emp.`employee_id`
ORDER BY emp.`employee_id`
LIMIT 5;

/* 2. Towns Addresses */

SELECT t.`town_id` AS `town_id`, t.`name` AS `town_name`, a.`address_text` AS `address_text`
FROM `addresses` AS a
JOIN `towns` AS t
ON a.`town_id` = t.`town_id`
WHERE t.`name` IN ('San Francisco', 'Sofia', 'Carnation')
ORDER BY t.town_id, a.address_id;

/* 3. Employees Without Manager */

SELECT `employee_id`, `first_name`, `last_name`, `department_id`, `salary`
FROM `employees`
WHERE `manager_id` IS NULL;

/* 4. Higher Salary */

SELECT COUNT(*) AS `count`
FROM `employees`
WHERE `salary` > 
(SELECT AVG(`salary`) FROM `employees`);