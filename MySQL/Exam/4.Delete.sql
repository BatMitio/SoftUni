DELETE FROM `employees`
WHERE `manager_id` IS NOT NULL AND `salary` >= 6000;

SELECT first_name, salary, hire_date, id
FROM employees;
