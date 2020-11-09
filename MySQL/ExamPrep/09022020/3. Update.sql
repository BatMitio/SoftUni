UPDATE `coaches` AS c
SET c.`coach_level` = c.`coach_level` + 1
WHERE (SELECT COUNT(*) AS `count_of_players`
FROM `players_coaches`
WHERE `coach_id` = c.`id`) >= 1 AND LEFT(c.`first_name`, 1) = 'A';

SELECT * 
FROM `coaches`
WHERE (SELECT COUNT(*) AS `count_of_players`
FROM `players_coaches`
WHERE `coach_id` = 5) >= 1 AND LEFT(`first_name`, 1) = 'A';

SELECT COUNT(*) AS `count_of_players`
FROM `players_coaches`
WHERE `coach_id` = 5;

SELECT c.id, c.first_name,c.coach_level FROM coaches as c
where id BETWEEN 1 AND 5
ORDER BY c.id;