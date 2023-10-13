SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE user_tb;
TRUNCATE TABLE interest_tb;
TRUNCATE TABLE mentor_post_tb;
TRUNCATE TABLE user_interest_tb;
TRUNCATE TABLE not_connected_register_user_tb;
SET REFERENTIAL_INTEGRITY TRUE;

-- user Table
INSERT INTO user_tb (created_at, first_name, last_name, email, password, country, introduction, age, profile_image, role, state) VALUES
   (NOW(), 'John', 'Doe', 'john@example.com', '{bcrypt}$2a$10$8H0OT8wgtALJkig6fmypi.Y7jzI5Y7W9PGgRKqnVeS2cLWGifwHF2', 'USA', 'Hello, I am John.', 25, 'profile.jpg', 'MENTOR', 'ACTIVE'),
   (NOW(), 'Alice', 'Smith', 'alice.smith@example.com', '{bcrypt}$2a$10$8H0OT8wgtALJkig6fmypi.Y7jzI5Y7W9PGgRKqnVeS2cLWGifwHF2', 'Canada', 'I love painting.', 25, 'image2.jpg', 'MENTOR', 'ACTIVE'),
   (NOW(), 'Admin', 'Admin', 'admin@example.com', '{bcrypt}$2a$10$8H0OT8wgtALJkig6fmypi.Y7jzI5Y7W9PGgRKqnVeS2cLWGifwHF2', 'USA', 'I am an admin user.', 35, 'admin.jpg', 'MENTEE', 'ACTIVE'),
   (NOW(), 'Jane', 'Smith', 'jane@example.com', '{bcrypt}$2a$10$8H0OT8wgtALJkig6fmypi.Y7jzI5Y7W9PGgRKqnVeS2cLWGifwHF2', 'Canada', 'I love coding.', 30, 'profile.jpg', 'MENTEE', 'ACTIVE'),
   (NOW(), 'Admin', 'Adminson', 'admin22222@example.com', '{bcrypt}$2a$10$8H0OT8wgtALJkig6fmypi.Y7jzI5Y7W9PGgRKqnVeS2cLWGifwHF2', 'USA', 'I am the admin.', 35, 'admin.jpg', 'MENTEE', 'ACTIVE');

-- interst Table
INSERT INTO interest_tb (created_at, category) VALUES
  (NOW(), 'IDOL'),
  (NOW(), 'Game'),
  (NOW(), 'K-POP'),
  (NOW(), 'Sports');

-- mentorPost Table
INSERT INTO mentor_post_tb (created_at, writer_id, title, content) VALUES
      (NOW(), 1, 'Teaching Programming', 'I can teach you how to code.'),
      (NOW(), 1, 'Art Workshop', 'Let''s create beautiful art together.'),
      (NOW(), 2, 'Software Development Mentorship', 'I can mentor you in software development.'),
      (NOW(), 2, 'Art and Painting Mentorship', 'Learn the art of painting with me.'),
      (NOW(), 1, 'Web Development Mentorship', 'I can teach you web development from scratch.'),
      (NOW(), 2, 'Fitness and Health Mentorship', 'Get in shape and stay healthy with my guidance.'),
      (NOW(), 1, 'Data Science Mentorship', 'Learn data science and machine learning with me.'),
      (NOW(), 2, 'Music Production Mentorship', 'Produce your own music with professional tips.'),
      (NOW(), 2, 'Cooking and Culinary Arts Mentorship', 'Master the art of cooking and culinary skills.'),
      (NOW(), 2, 'Entrepreneurship Mentorship', 'Start and grow your own business.'),
      (NOW(), 1, 'Graphic Design Mentorship', 'Create stunning graphics and designs.'),
      (NOW(), 1, 'Yoga and Mindfulness Mentorship', 'Find inner peace and balance through yoga.'),
      (NOW(), 1, 'Photography Mentorship', 'Capture amazing moments with your camera.'),
      (NOW(), 2, 'Mathematics Tutoring', 'I can help you understand and excel in math.');;


-- notConnectedRegisterUser Table
INSERT INTO not_connected_register_user_tb (created_at, mentor_post_id, mentee_user_id, state) VALUES
      (NOW(), 1, 3, 'AWAIT'),
      (NOW(), 1, 4, 'ACCEPT'),
      (NOW(), 2, 5, 'AWAIT');

-- userInterest Table
INSERT INTO user_interest_tb (created_at, user_id, interest_id) VALUES
   (NOW(), 1, 1),
   (NOW(), 1, 2),
   (NOW(), 2, 1),
   (NOW(), 2, 3),
   (NOW(), 3, 3),
   (NOW(), 3, 4),
   (NOW(), 4, 1),
   (NOW(), 4, 3),
   (NOW(), 5, 3),
   (NOW(), 5, 4)
;