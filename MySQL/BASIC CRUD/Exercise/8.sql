SELECT `first_name`, `last_name`, `job_title` AS 'JobTitle' FROM `employees`
WHERE `salary` BETWEEN 20000 AND 30000
ORDER BY `employee_id`;
