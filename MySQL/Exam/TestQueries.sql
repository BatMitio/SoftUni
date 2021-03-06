SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = 'softuni_stores_system'
ORDER BY COLUMN_NAME, TABLE_NAME;

SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = 'softuni_stores_system'
ORDER BY COLUMN_NAME, TABLE_NAME;

SELECT COLUMN_KEY FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = 'softuni_stores_system'
  AND COLUMN_NAME IN ('id','product_id','store_id',
                      'town_id', 'address_id', 'category_id', 'picture_id','manager_id')
ORDER BY COLUMN_NAME, TABLE_NAME DESC, COLUMN_KEY DESC;
