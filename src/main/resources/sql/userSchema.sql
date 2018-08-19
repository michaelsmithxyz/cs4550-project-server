CREATE TABLE app_user (
  id INT PRIMARY KEY AUTO_INCREMENT,
  facebook_id VARCHAR(255) DEFAULT NULL,
  role VARCHAR(100) NOT NULL,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) DEFAULT NULL,
  password VARCHAR(255) DEFAULT NULL,
  profile_picture BLOB DEFAULT NULL,
  joined DATE DEFAULT NULL
);

CREATE TABLE user_follower_map (
  follower_id INT REFERENCES app_user,
  target_id INT REFERENCES app_user
);