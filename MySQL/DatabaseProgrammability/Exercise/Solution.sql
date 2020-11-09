DROP PROCEDURE IF EXISTS usp_get_employees_salary_above_35000;
DELIMITER $$
CREATE PROCEDURE usp_get_employees_salary_above_35000()
BEGIN
	SELECT `first_name`, `last_name`
    FROM `employees`
    WHERE `salary` > 35000
    ORDER BY `first_name`, `last_name`, `employee_id`; 
END$$
DELIMITER ;

CALL usp_get_employees_salary_above_35000();



DROP PROCEDURE IF EXISTS usp_get_employees_salary_above;
DELIMITER $$
CREATE PROCEDURE usp_get_employees_salary_above(IN threshold DECIMAL(12, 4))
BEGIN
	SELECT `first_name`, `last_name`
    FROM `employees`
    WHERE `salary` >= threshold
    ORDER BY `first_name`, `last_name`, `employee_id`; 
END$$
DELIMITER ;

CALL usp_get_employees_salary_above(48100);

DROP PROCEDURE IF EXISTS usp_get_towns_starting_with;
DELIMITER $$
CREATE PROCEDURE usp_get_towns_starting_with(start_with VARCHAR(50))
BEGIN
SELECT `name` AS `town_name`
FROM `towns`
WHERE LOCATE(start_with, `name`) = 1
ORDER BY `name`;
END$$
DELIMITER ;

CALL usp_get_towns_starting_with('b');





DROP PROCEDURE IF EXISTS usp_get_employees_from_town;
DELIMITER $$

CREATE PROCEDURE usp_get_employees_from_town(town_name VARCHAR(50))
BEGIN
SELECT e.`first_name`, e.`last_name`
FROM `employees` AS e
JOIN `addresses` AS a
ON e.`address_id` = a.`address_id`
JOIN `towns` AS t
ON a.`town_id` = t.`town_id`
WHERE t.`name` = town_name
ORDER BY e.`first_name`, e.`last_name`, e.`employee_id`;
END$$

DELIMITER ;

CALL usp_get_employees_from_town('Sofia');


DROP FUNCTION IF EXISTS ufn_get_salary_level;
DELIMITER $$
CREATE FUNCTION ufn_get_salary_level(salary DECIMAL(19, 4))
RETURNS VARCHAR(10)
DETERMINISTIC
BEGIN
	IF(salary < 30000)
		THEN RETURN 'Low';
	ELSEIF(salary BETWEEN 30000 AND 50000)
		THEN RETURN 'Average';
	ELSE 
		RETURN 'High';
	END IF;
END$$
DELIMITER ;

SELECT ufn_get_salary_level(13500);
SELECT ufn_get_salary_level(50001);
SELECT ufn_get_salary_level(125500);


DROP PROCEDURE IF EXISTS usp_get_employees_by_salary_level;
DELIMITER $$

CREATE PROCEDURE usp_get_employees_by_salary_level(level_of_salary VARCHAR(10))
BEGIN
SELECT `first_name`, `last_name`
FROM `employees`
WHERE LOWER(ufn_get_salary_level(`salary`)) = level_of_salary
ORDER BY `first_name` DESC, `last_name` DESC;
END$$

DELIMITER ;

CALL usp_get_employees_by_salary_level('High');



DROP FUNCTION IF EXISTS ufn_is_word_comprised;
DELIMITER $$
CREATE FUNCTION ufn_is_word_comprised(set_of_letters VARCHAR(50), word VARCHAR(50))
RETURNS BOOLEAN
DETERMINISTIC
BEGIN
	RETURN REGEXP_LIKE(LOWER(word), concat('^[', set_of_letters, ']+$'));
END$$
DELIMITER ;

SELECT ufn_is_word_comprised('oistmiahf', 'Sofia');


DROP PROCEDURE IF EXISTS usp_get_holders_full_name;
DELIMITER $$
CREATE PROCEDURE usp_get_holders_full_name()
BEGIN
	SELECT concat_ws(' ', `first_name`, `last_name`) AS `full_name`
    FROM `account_holders`
    ORDER BY `full_name`;
END$$
DELIMITER ;


DROP FUNCTION IF EXISTS ufn_calculate_future_value;
DELIMITER $$
CREATE FUNCTION ufn_calculate_future_value(initial_sum DECIMAL(19, 4), yearly_interest_rate DOUBLE, number_of_years INT)
RETURNS DECIMAL(19, 4)
DETERMINISTIC
BEGIN
RETURN initial_sum * POWER(1 + yearly_interest_rate, number_of_years);
END$$
DELIMITER ;

SELECT ufn_calculate_future_value(1000, 0.5, 5);


DROP PROCEDURE IF EXISTS usp_withdraw_money;
DELIMITER $$
CREATE PROCEDURE usp_withdraw_money(account_id INT, money_amount DECIMAL(19, 4))
BEGIN
START TRANSACTION;
IF(money_amount < 0 OR (SELECT `balance`
						FROM `accounts`
						WHERE `id` = account_id) < money_amount)
THEN 
	ROLLBACK;
ELSE
	UPDATE `accounts`
	SET `balance` = `balance` - money_amount
	WHERE `id` = account_id;
    COMMIT;
END IF;
END$$
DELIMITER ;

CALL usp_withdraw_money(1, 10);

DELIMITER $$
CREATE TRIGGER tr_deleted_employees
BEFORE DELETE
ON employees
FOR EACH ROW
BEGIN
INSERT INTO deleted_employees(first_name,last_name,middle_name,job_title,department_id,salary)
VALUES(OLD.first_name,OLD.last_name,OLD.middle_name,OLD.job_title,OLD.department_id,OLD.salary);
END;$$
DELIMITER ;

SET foreign_key_checks = 0;

DELETE FROM `employees`
WHERE `employee_id` = 1;
