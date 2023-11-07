SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE users;
TRUNCATE TABLE interests;
TRUNCATE TABLE mentor_posts;
TRUNCATE TABLE user_interests;
TRUNCATE TABLE not_connected_register_users;
TRUNCATE TABLE connected_users;
SET REFERENTIAL_INTEGRITY TRUE;

-- user Table
INSERT INTO users (created_at, first_name, last_name, email, password, country, introduction, birth_date, profile_image, role, phone) VALUES
 (NOW(), 'John', 'Doe', 'test1@example.com', '{bcrypt}$2a$10$...', 'USA', 'Hello, I am John.', '1998-01-01', 'profile.jpg', 'MENTOR', '010-0000-0000'),
 (NOW(), 'Alice', 'Smith', 'test2@example.com', '{bcrypt}$2a$10$...', 'Canada', 'I love painting.', '1998-01-01', 'image2.jpg', 'MENTOR', '010-0000-0000'),
 (NOW(), 'Admin', 'Admin', 'test3@example.com', '{bcrypt}$2a$10$...', 'USA', 'I am an admin user.', '1988-01-01', 'admin.jpg', 'MENTEE', '010-0000-0000'),
 (NOW(), 'Jane', 'Smith', 'test4@example.com', '{bcrypt}$2a$10$...', 'Canada', 'I love coding.', '1993-01-01', 'profile.jpg', 'MENTEE', '010-0000-0000'),
 (NOW(), 'Adccczczmin', 'qwdasd', 'test5@example.com', '{bcrypt}$2a$10$...', 'USA', 'I am the qwdad.', '1988-01-01', 'admin.jpg', 'MENTEE', '010-0000-0000'),
 (NOW(), 'adadad', 'adaddddd', 'test6@example.com', '{bcrypt}$2a$10$...', 'USA', 'I am the adadad.', '1988-01-01', 'admin.jpg', 'MENTEE', '010-0000-0000'),
(NOW(), 'admin', 'admin', 'admin@example.com', '{bcrypt}$2a$10$...', 'USA', 'I am the admin.', '1988-01-01', 'admin.jpg', 'ADMIN', '010-0000-0000');

-- interst Table
INSERT INTO interests (created_at, category) VALUES
  (NOW(), 'IDOL'),
  (NOW(), 'Game'),
  (NOW(), 'K-POP'),
  (NOW(), 'Sports');

-- mentorPost Table
INSERT INTO mentor_posts (created_at, writer_id, title, content, state) VALUES
      (NOW(), 1, 'Teaching Programming', 'I can teach you how to code.', 'ACTIVE'),
      (NOW(), 1, 'Art Workshop', 'Let''s create beautiful art together.', 'DONE'),
      (NOW(), 2, 'Software Development Mentorship', 'I can mentor you in software development.', 'ACTIVE'),
      (NOW(), 2, 'Art and Painting Mentorship', 'Learn the art of painting with me.', 'ACTIVE'),
      (NOW(), 1, 'Web Development Mentorship', 'I can teach you web development from scratch.', 'ACTIVE'),
      (NOW(), 2, 'Fitness and Health Mentorship', 'Get in shape and stay healthy with my guidance.', 'ACTIVE'),
      (NOW(), 1, 'Data Science Mentorship', 'Learn data science and machine learning with me.', 'ACTIVE'),
      (NOW(), 2, 'Music Production Mentorship', 'Produce your own music with professional tips.', 'ACTIVE'),
      (NOW(), 2, 'Cooking and Culinary Arts Mentorship', 'Master the art of cooking and culinary skills.', 'ACTIVE'),
      (NOW(), 2, 'Entrepreneurship Mentorship', 'Start and grow your own business.', 'ACTIVE'),
      (NOW(), 1, 'Graphic Design Mentorship', 'Create stunning graphics and designs.', 'ACTIVE'),
      (NOW(), 1, 'Yoga and Mindfulness Mentorship', 'Find inner peace and balance through yoga.', 'ACTIVE'),
      (NOW(), 1, 'Photography Mentorship', 'Capture amazing moments with your camera.', 'ACTIVE'),
      (NOW(), 2, 'Mathematics Tutoring', 'I can help you understand and excel in math.', 'ACTIVE');


-- notConnectedRegisterUser Table
INSERT INTO not_connected_register_users (created_at, mentor_post_id, mentee_user_id, state) VALUES
      (NOW(), 1, 3, 'AWAIT'),
      (NOW(), 3, 3, 'AWAIT'),
      (NOW(), 1, 4, 'ACCEPT'),
      (NOW(), 3, 5, 'AWAIT');

-- connectedUser Table
INSERT INTO connected_users (created_at, mentor_post_id, mentee_user_id) VALUES
    (NOW(), 2, 3),
    (NOW(), 3, 3),
    (NOW(), 2, 5),
    (NOW(), 2, 6);

-- userInterest Table
INSERT INTO user_interests (created_at, user_id, interest_id) VALUES
   (NOW(), 1, 1),
   (NOW(), 1, 2),
   (NOW(), 2, 1),
   (NOW(), 2, 3),
   (NOW(), 3, 3),
   (NOW(), 3, 4),
   (NOW(), 4, 1),
   (NOW(), 4, 3),
   (NOW(), 5, 3),
   (NOW(), 5, 4),
   (NOW(), 5, 3),
   (NOW(), 5, 4);
