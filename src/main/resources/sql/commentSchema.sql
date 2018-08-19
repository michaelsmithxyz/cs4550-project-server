CREATE TABLE comment (
  id INT PRIMARY KEY AUTO_INCREMENT,
  author_id INT REFERENCES app_user,
  posted DATE DEFAULT NULL,
  text TEXT NOT NULL,
  recipe_id INT REFERENCES recipe
);