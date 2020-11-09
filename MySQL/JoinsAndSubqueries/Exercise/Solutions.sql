/* 1. Employee Address */

SELECT e.`employee_id`, e.`job_title`, e.`address_id`, a.`address_text`
FROM `employees` AS e
JOIN `addresses` AS a
ON e.`address_id` = a.`address_id`
ORDER BY a.`address_id`
LIMIT 5;

/* 2. Addresses with Towns */

SELECT e.`first_name`, e.`last_name`, t.`name`, a.`address_text`
FROM `employees` AS e
JOIN `addresses` AS a
ON a.`address_id` = e.`address_id`
JOIN `towns` AS t
ON t.`town_id` = a.`town_id`
ORDER BY `first_name` ASC, `last_name`
LIMIT 5;

/* 3. Sales Employee */

SELECT e.`employee_id`, e.`first_name`, e.`last_name`, d.`name` AS `department_name`
FROM `employees` AS e
JOIN `departments` AS d
ON d.`department_id` = e.`department_id`
WHERE d.`name` = 'Sales'
ORDER BY e.`employee_id` DESC;

/* 4. Employee Departments */

SELECT `employee_id`, `first_name`, `salary`, `name`
FROM `employees` AS e
JOIN `departments` AS d
ON e.`department_id` = d.`department_id`
WHERE `salary` > 15000
ORDER BY d.`department_id` DESC
LIMIT 5;

/* 5. Employees Without Project */

SELECT DISTINCT e.`employee_id`, e.`first_name`
FROM `employees_projects` AS ep
RIGHT JOIN `employees` AS e
ON e.`employee_id` = ep.`employee_id`
WHERE ep.`employee_id` IS NULL
ORDER BY e.`employee_id` DESC
LIMIT 3;

/* 6. Employees Hired After */

SELECT e.`first_name`, e.`last_name`, e.`hire_date`, d.`name`
FROM `departments` AS d
JOIN `employees` AS e
ON e.`department_id` = d.`department_id`
WHERE e.`hire_date` > '1999-01-01' AND (d.`name` IN ('Sales', 'Finance'))
ORDER BY e.`hire_date` ASC;

/* 7. Employees with Project */

SELECT e.`employee_id`, e.`first_name`, p.`name` AS `project_name`
FROM `employees` AS e
JOIN `employees_projects` AS ep
ON e.`employee_id` = ep.`employee_id`
JOIN `projects` AS p
ON ep.`project_id` = p.`project_id`
WHERE p.`start_date` > '2002-08-13' AND p.`end_date` IS NULL
ORDER BY e.`first_name` ASC , p.`name` ASC
LIMIT 5;

/* 8. Employee 24 */

SELECT e.`employee_id`, e.`first_name`, IF(EXTRACT(YEAR FROM p.`start_date`) >= 2005, NULL, p.`name`) AS `project_name`
FROM `employees` AS e
JOIN `employees_projects` AS ep
ON ep.`employee_id` = e.`employee_id`
JOIN `projects` AS p
ON p.`project_id` = ep.`project_id`
WHERE e.`employee_id` = 24
ORDER BY `project_name` ASC;

/* 9. Employee Manager */

SELECT e.`employee_id`, e.`first_name`, m.`employee_id`, m.`first_name`
FROM `employees` AS e
JOIN `employees` AS m
ON e.`manager_id` = m.`employee_id`
WHERE e.`manager_id` IN (3, 7)
ORDER BY e.`first_name`;

/* 10. Employee Summary */

SELECT e.`employee_id`, concat_ws(' ', e.`first_name`, e.`last_name`) AS `employee_name`, concat_ws(' ', m.`first_name`, m.`last_name`) AS `manager_name`, d.`name` 
FROM `employees` AS e
JOIN `employees` AS m
ON e.`manager_id` = m.`employee_id`
JOIN `departments` AS d
ON d.`department_id` = e.`department_id`
ORDER BY e.`employee_id`
LIMIT 5;

/* 11. Min Average Salary */

SELECT AVG(e.`salary`) AS `min_average_salary`
FROM `employees` AS e
JOIN `departments` AS d
ON e.`department_id` = d.`department_id`
GROUP BY d.`department_id`
ORDER BY `min_average_salary` ASC
LIMIT 1;

/* 12. Highest Peaks in Bulgaria */

SELECT c.`country_code`, m.`mountain_range`, p.`peak_name`, p.`elevation`
FROM `countries` c
JOIN `mountains_countries` mc
ON mc.`country_code` = c.`country_code`
JOIN `mountains` m
ON m.`id` = mc.`mountain_id`
JOIN `peaks` p
ON p.`mountain_id` = m.`id`
WHERE c.`country_code` = 'BG' AND p.`elevation` > 2835
ORDER BY p.`elevation` DESC;

/* 13. Count Mountain Ranges */

SELECT c.`country_code`, COUNT(m.`mountain_range`) AS `mountain_range`
FROM `countries` c
JOIN `mountains_countries` mc
ON mc.`country_code` = c.`country_code`
JOIN `mountains` m
ON m.`id` = mc.`mountain_id`
WHERE c.`country_code` IN ('US', 'BG', 'RU')
GROUP BY c.`country_code`
ORDER BY `mountain_range` DESC;

/* 14. Countries with Rivers */

SELECT c.`country_name`, r.`river_name`
FROM `countries` AS c
LEFT JOIN `countries_rivers` AS cr
ON cr.`country_code` = c.`country_code`
LEFT JOIN `rivers` AS r
ON r.`id` = cr.`river_id`
WHERE c.`continent_code` = 'AF'
ORDER BY c.`country_name`
LIMIT 5;

/* 15. Constinents and Currencies */

SELECT `continent_code`, (SELECT `currency_code`
	FROM `countries` AS `inner` 
    WHERE `continent_code` = `outer`.`continent_code`
	GROUP BY `currency_code`
    ORDER BY COUNT(`currency_code`) DESC
    LIMIT 1) AS `currency_code`,
    (SELECT COUNT(`currency_code`)
	FROM `countries` AS `inner` 
    WHERE `continent_code` = `outer`.`continent_code`
	GROUP BY `currency_code`
    ORDER BY COUNT(`currency_code`) DESC
    LIMIT 1) AS `currency_usage`
FROM `countries` AS `outer`
GROUP BY `continent_code`
HAVING `currency_usage` > 1
ORDER BY `continent_code`, `currency_code`;

/* 16. Countries without any Mountains */

SELECT COUNT(*) AS `country_count`
FROM (
	SELECT c.`country_code`
	FROM `countries` AS c
	LEFT JOIN `mountains_countries` AS mc
	ON mc.`country_code` = c.`country_code`
	GROUP BY c.`country_code`
	HAVING COUNT(mc.`mountain_id`) = 0
) AS `table`;

/* 17. Highest Peak and Longest River by Country */

SELECT c.`country_name` AS `country_name`, MAX(p.`elevation`) AS `highest_peak_elevation`, MAX(r.`length`) AS `longest_river_length`
FROM `countries` AS c
LEFT JOIN `mountains_countries` AS mc
ON mc.`country_code` = c.`country_code`
LEFT JOIN `mountains` AS m
ON m.`id` = mc.`mountain_id`
LEFT JOIN `peaks` AS p
ON p.`mountain_id` = m.`id`
LEFT JOIN `countries_rivers` cr
ON cr.`country_code` = c.`country_code`
LEFT JOIN `rivers` AS r
ON r.`id` = cr.`river_id`
GROUP BY c.`country_name`
ORDER BY `highest_peak_elevation` DESC, `longest_river_length` DESC, c.`country_name` ASC
LIMIT 5;


