SELECT c.`name`, COUNT(pl.`id`) AS `total_count_of_players`, SUM(pl.`salary`) AS `total_sum_of_salaries`
FROM `countries` AS c
LEFT JOIN `towns` AS twn
ON twn.`country_id` = c.`id`
LEFT JOIN `stadiums` AS st
ON st.`town_id` = twn.`id`
LEFT JOIN `teams` AS te
ON te.`stadium_id` = st.`id`
LEFT JOIN `players` AS pl
ON pl.`team_id` = te.`id`
GROUP BY c.`name`
ORDER BY `total_count_of_players` DESC, c.`name` ASC;