DROP FUNCTION IF EXISTS ufn_count_employees_by_town;
DELIMITER $$
CREATE FUNCTION ufn_count_employees_by_town(town_name VARCHAR(50))
RETURNS INT 
DETERMINISTIC
BEGIN
	DECLARE e_count INT;
	SET e_count := (
    SELECT COUNT(employee_id) 
    FROM employees AS e
	JOIN addresses AS a 
	ON a.address_id = e.address_id
	JOIN towns AS t 
	ON t.town_id = a.town_id
	WHERE t.name = town_name);
	RETURN e_count;
END$$
DELIMITER ;

SELECT ufn_count_employees_by_town('Sofia');


DROP PROCEDURE IF EXISTS usp_select_employees_by_seniority;
DELIMITER $$
CREATE PROCEDURE usp_select_employees_by_seniority()
BEGIN
SELECT *
FROM employees
WHERE ROUND(DATEDIFF(NOW(), hire_date) / 365.25) < 30;
END $$
DELIMITER ;

CALL usp_select_employees_by_seniority();


DROP PROCEDURE IF EXISTS usp_raise_salaries;
DELIMITER //
CREATE PROCEDURE usp_raise_salaries(department_name VARCHAR(50))
BEGIN
	UPDATE employees AS e JOIN departments AS d ON e.department_id = d.department_id
    SET salary = salary * 1.05
    WHERE d.name = department_name;
END//
DELIMITER ;


CALL usp_raise_salaries('Finance');

SELECT `first_name`, `salary`
FROM `employees`
WHERE `department_id` = 10
ORDER BY `first_name`, `salary`;

DROP PROCEDURE IF EXISTS usp_raise_salary_by_id;
DELIMITER //
CREATE PROCEDURE usp_raise_salary_by_id(IN id INT)
BEGIN
UPDATE employees
SET salary = salary * 1.05
WHERE employee_id = id;
END//
DELIMITER ;

CALL usp_raise_salary_by_id(178);

SELECT `salary`
FROM `employees`
WHERE `employee_id` = 178;

CREATE TABLE deleted_employees(
	employee_id INT PRIMARY KEY AUTO_INCREMENT,
	first_name VARCHAR(20),
	last_name VARCHAR(20),
	middle_name VARCHAR(20),
	job_title VARCHAR(50),
	department_id INT,
	salary DOUBLE 
);

DELIMITER $$
CREATE TRIGGER tr_deleted_employees
AFTER DELETE
ON employees
FOR EACH ROW
BEGIN
	INSERT INTO deleted_employees(first_name, last_name, middle_name, job_title, department_id, salary)
    VALUES
    (OLD.first_name, OLD.last_name, OLD.middle_name, OLD.job_title, OLD.department_id, OLD.salary);
END$$
DELIMITER ;

DELETE FROM employees
WHERE employee_id = 1;


ALTER TABLE employees
DROP CONSTRAINT fk_employees_addresses;
