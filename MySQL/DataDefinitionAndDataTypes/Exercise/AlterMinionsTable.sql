USE minions;

ALTER TABLE minions
ADD town_id INT NOT NULL,
ADD CONSTRAINT fk_town FOREIGN KEY (town_id) REFERENCES towns(town_id);