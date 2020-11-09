SELECT reverse(s.`name`) AS `reversed_name`, CONCAT(UPPER(t.`name`), '-', addr.`name`), COUNT(e.`id`), MIN(p.`price`) AS `min_price`, COUNT(p.`id`), DATE_FORMAT(MAX(pi.`added_on`), "%D-%b-%Y")
FROM `employees` AS e
RIGHT JOIN `stores` AS s
ON s.`id` = e.`store_id`
JOIN `addresses` AS addr
ON addr.`id` = s.`address_id`
JOIN `towns` AS t
ON t.`id` = addr.`town_id`
JOIN `products_stores` AS ps
ON ps.`store_id` = s.`id`
JOIN `products` AS p
ON p.`id` = ps.`product_id`
JOIN `pictures` AS pi
ON p.`picture_id` = pi.`id`
GROUP BY s.`name`
HAVING MIN(p.`price`) > 10
ORDER BY `reversed_name` ASC, `min_price`;