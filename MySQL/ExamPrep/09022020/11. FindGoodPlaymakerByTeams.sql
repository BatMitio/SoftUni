DROP PROCEDURE IF EXISTS udp_find_playmaker;
DELIMITER $$
CREATE PROCEDURE udp_find_playmaker(min_dribble_points INT, team_name VARCHAR(45))
BEGIN
SELECT concat_ws(' ', p.`first_name`, p.`last_name`) AS `full_name`, p.`age`, p.`salary`, sd.`dribbling`, sd.`speed`, t.`name`
FROM `players` AS p
JOIN `skills_data` AS sd
ON p.`skills_data_id` = sd.`id`
JOIN `teams` AS t
ON p.`team_id` = t.`id`
WHERE t.`name` = team_name AND sd.`dribbling` > min_dribble_points AND sd.`speed` > (SELECT AVG(`speed`) FROM `skills_data`)
ORDER BY sd.`speed` DESC
LIMIT 1;
END$$
DELIMITER ;

CALL udp_find_playmaker (20, 'Skyble');