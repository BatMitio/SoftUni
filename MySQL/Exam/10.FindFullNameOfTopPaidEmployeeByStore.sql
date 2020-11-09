DROP FUNCTION IF EXISTS udf_top_paid_employee_by_store;
DELIMITER $$
CREATE FUNCTION udf_top_paid_employee_by_store(store_name VARCHAR(50))
RETURNS VARCHAR(100)
DETERMINISTIC
BEGIN
DECLARE full_name VARCHAR(40);
SET full_name := (
	SELECT concat(`first_name`, ' ', `middle_name`, '. ', `last_name`)
	FROM `employees` AS e
	JOIN `stores` AS s
	ON s.`id` = e.`store_id`
	WHERE s.`name` = store_name
	ORDER BY e.`salary` DESC
	LIMIT 1
);
RETURN concat(full_name, ' works in store for ', FLOOR(DATEDIFF('2020-10-18', (SELECT `hire_date`
															FROM `employees` AS e
															JOIN `stores` AS s
															ON s.`id` = e.`store_id`
															WHERE s.`name` = store_name
															ORDER BY e.`salary` DESC
															LIMIT 1)) / 365.25), ' years');
END$$
DELIMITER ;

SELECT `hire_date`
	FROM `employees` AS e
	JOIN `stores` AS s
	ON s.`id` = e.`store_id`
	WHERE s.`name` = 'Stronghold'
	ORDER BY e.`salary` DESC
	LIMIT 1;
    
    
SELECT udf_top_paid_employee_by_store('Stronghold') as 'full_info';
SELECT udf_top_paid_employee_by_store('Keylex') as 'full_info';