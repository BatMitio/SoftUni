SELECT concat_ws(' ', `first_name`, `last_name`) AS `Full_name`, s.`name` AS `Store_name`, a.`name` AS `address`, e.`salary`
FROM `employees` AS e
JOIN `stores` AS s
ON s.`id` = e.`store_id`
JOIN `addresses` AS a
ON a.`id` = s.`address_id`
WHERE e.`salary` < 7000 AND LOCATE('a', LOWER(a.`name`)) != 0 AND CHAR_LENGTH(s.`name`) > 5;