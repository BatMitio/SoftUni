INSERT INTO `products_stores` (`store_id`, `product_id`)
SELECT 1, `id`
FROM `products`
WHERE `id` NOT IN (SELECT `product_id` FROM `products_stores`);


SELECT 1, `id`
FROM `products`
WHERE `id` NOT IN (SELECT `product_id` FROM `products_stores`);

SELECT store_id, s.name, p.name, product_id FROM products_stores
JOIN products p ON p.id = products_stores.product_id
JOIN stores s ON products_stores.store_id = s.id
ORDER BY product_id, store_id;
