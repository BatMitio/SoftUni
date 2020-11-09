DROP PROCEDURE IF EXISTS udp_update_product_price;
DELIMITER $$
CREATE PROCEDURE udp_update_product_price(address_name VARCHAR(50))
BEGIN
	IF(LEFT(address_name, 1) = '0')
    THEN
    UPDATE `addresses` AS a
	JOIN `stores` AS s
	ON s.`address_id` = a.`id`
	JOIN `products_stores` AS ps
	ON ps.`store_id` = s.`id`
	JOIN `products` AS p
	ON p.`id` = ps.`product_id`
    SET p.`price` = p.`price` + 100
	WHERE a.`name` = address_name;
    ELSE
    UPDATE `addresses` AS a
	JOIN `stores` AS s
	ON s.`address_id` = a.`id`
	JOIN `products_stores` AS ps
	ON ps.`store_id` = s.`id`
	JOIN `products` AS p
	ON p.`id` = ps.`product_id`
    SET p.`price` = p.`price` + 200
	WHERE a.`name` = address_name;
    END IF;
	
END$$
DELIMITER ;

CALL udp_update_product_price('07 Armistice Parkway');
SELECT name, price FROM products WHERE id = 15;

CALL udp_update_product_price('1 Cody Pass');
SELECT name, price FROM products WHERE id = 17;
