
SELECT *
FROM `employees`;

UPDATE `employees`
SET `manager_id` = 3, `salary` = `salary` - 500
WHERE EXTRACT(YEAR FROM `hire_date`) > 2003 AND `store_id` NOT IN (5, 14);

SELECT first_name, salary, hire_date, id 
FROM employees 
WHERE manager_id = 3;
