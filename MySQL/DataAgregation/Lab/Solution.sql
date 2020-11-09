SELECT *
FROM `employees`
GROUP BY `department_id`
ORDER BY `department_id`;

SELECT `department_id`, ROUND(AVG(`salary`), 2)
FROM `employees`
GROUP BY `department_id`
ORDER BY `department_id`;

SELECT `department_id`, ROUND(MIN(`salary`), 2) AS `Min Salary`
FROM `employees`
GROUP BY `department_id`
HAVING MIN(`salary`) > 800;


SELECT COUNT(`category_id`) AS `Count`
FROM `products`
WHERE `price` > 8 AND `category_id` = 2;


SELECT `category_id`, 
ROUND(AVG(`price`), 2) AS `Average Price`, 
ROUND(MIN(`price`), 2) AS `Cheapest Product`, 
ROUND(MAX(`price`), 2) AS `Most Expensive Product`
FROM `products`
GROUP BY `category_id`;




