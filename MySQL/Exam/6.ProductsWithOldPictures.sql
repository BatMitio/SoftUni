SELECT pr.`name`, pr.`price`, pr.`best_before`, CONCAT(SUBSTRING(pr.`description`, 1, 10), '...') AS `short_description`, pi.`url`
FROM `products` AS pr
JOIN `pictures` AS pi
ON pr.`picture_id` = pi.`id`
WHERE CHAR_LENGTH(pr.`description`) > 100 AND YEAR(pi.`added_on`) < 2019 AND pr.`price` > 20
ORDER BY pr.`price` DESC;