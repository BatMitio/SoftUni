SELECT `title`
FROM `books`
WHERE SUBSTRING(`title`, 1, 3) = 'The';

SELECT REPLACE(`title`, 'The', '***') AS 'title'
FROM `books`
WHERE SUBSTRING(`title`, 1, 3) = 'The';

SELECT ROUND(SUM(`cost`), 2) AS 'Total cost' FROM `books`;

SELECT 
concat_ws(' ', `first_name`, `last_name`) AS 'Full name', 
IF(`died` = NULL, '(NULL)', TIMESTAMPDIFF(DAY, `born`, `died`)) AS 'Days Lived'
FROM `authors`;

SELECT `title`
FROM `books`
WHERE LOCATE('Harry Potter', `title`) != 0;