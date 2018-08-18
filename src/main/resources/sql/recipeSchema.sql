CREATE TABLE recipe (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  author_id INT REFERENCES app_user,
  duration VARCHAR(255) DEFAULT NULL,
  yield VARCHAR(255) DEFAULT NULL
);

CREATE TABLE recipe_ingredient_map (
  id INT PRIMARY KEY AUTO_INCREMENT,
  recipe_id INT REFERENCES recipe,
  ingredient_id INT REFERENCES ingredient,
  quantity VARCHAR(255),
  modifier VARCHAR(255)
);

CREATE TABLE recipe_step (
  id INT PRIMARY KEY AUTO_INCREMENT,
  recipe_id INT REFERENCES recipe,
  sort_order INT NOT NULL,
  text TEXT NOT NULL
);