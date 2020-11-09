SELECT MAX(sd.speed) AS `max_speed`, twn.`name`
FROM `towns` AS twn
LEFT JOIN `stadiums` AS st
ON st.`town_id` = twn.`id`
LEFT JOIN `teams` AS te
ON te.`stadium_id` = st.`id`
LEFT JOIN `players` pl
ON pl.`team_id` = te.`id`
LEFT JOIN `skills_data` sd
ON sd.`id` = pl.`skills_data_id`
WHERE te.`name` != 'Devify'
GROUP BY twn.`name`
ORDER BY `max_speed` DESC, twn.`name`;


SELECT MAX(sd.`speed`) AS `max_speed`, t1.`name` AS `town_name`
FROM `players` AS p
RIGHT JOIN `skills_data` AS sd
ON p.`skills_data_id` = sd.`id`
RIGHT JOIN `teams` AS t
ON p.`team_id` = t.`id`
RIGHT JOIN `stadiums` AS s
ON t.`stadium_id` = s.`id`
RIGHT JOIN `towns` AS t1
ON s.`town_id` = t1.`id`
WHERE t.`name` != 'Devify'
GROUP BY t1.`name`
ORDER BY `max_speed` DESC, t1.`name` ASC;