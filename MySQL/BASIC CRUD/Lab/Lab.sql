SELECT id, first_name, last_name, job_title 
FROM employees
ORDER BY id;

SELECT id, concat(first_name, ' ', last_name) AS 'Full Name', 
job_title AS 'Job title',
salary AS 'Salary'
FROM employees
WHERE salary > 1000;

SELECT id, first_name, last_name, job_title, department_id, salary
FROM employees
WHERE department_id = 4
ORDER BY id;

SELECT * FROM employees;

UPDATE employees
SET salary = salary + 100
WHERE job_title = 'Manager';
SELECT salary FROM employees;

CREATE VIEW `v_hr_result_set` AS
SELECT
	CONCAT(`first_name`, ' ', `last_name`) AS 'Full Name', `salary`
FROM `employees` ORDER BY `department_id`;

CREATE VIEW `v_top_paid_employee` AS
SELECT id, first_name, last_name, job_title, department_id, salary
FROM employees
ORDER BY salary DESC
LIMIT 1; 

SELECT * FROM `v_top_paid_employee`;

DELETE FROM employees
WHERE department_id IN (1, 2);
SELECT id, first_name AS First_name, last_name AS Last_name, job_title AS Job_title, department_id AS Department_id, salary
FROM employees
ORDER BY id