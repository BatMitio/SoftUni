DROP FUNCTION IF EXISTS udf_stadium_players_count;
DELIMITER $$
CREATE FUNCTION udf_stadium_players_count(stadium_name VARCHAR(30))
RETURNS INT
DETERMINISTIC
BEGIN
SET @answer = 
(SELECT COUNT(pl.`id`)
FROM `players` AS pl
JOIN `teams` AS te
ON pl.`team_id` = te.`id`
JOIN `stadiums` AS st
ON te.`stadium_id` = st.`id`
GROUP BY st.`name`
HAVING st.`name` = stadium_name); 
RETURN(IF(@answer IS NULL, 0, @answer));
END$$
DELIMITER ;

SELECT udf_stadium_players_count ('Jaxworks') as `count`; 

SELECT udf_stadium_players_count ('Linklinks') as `count`; 


DROP FUNCTION IF EXISTS udf_stadium_players_count;
DELIMITER //
CREATE FUNCTION udf_stadium_players_count(stadium_name VARCHAR(30))
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE players_count INT;
    SET players_count := (
    SELECT COUNT(p.`id`)
    FROM `players` AS p
    INNER JOIN `teams` AS t
    ON p.`team_id` = t.`id`
    INNER JOIN `stadiums` AS s
    ON t.`stadium_id` = s.`id`
    WHERE s.`name` = stadium_name
    );
    RETURN players_count;
END//
DELIMITER ;