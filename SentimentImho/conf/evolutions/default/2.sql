# --- First database schema

# --- !Ups
INSERT INTO
  users (id, user_id, provider_id, firstname, lastname, fullname, email, auth_method, hasher, password)
VALUES
  (nextval('user_id'), 'test@gmail.com', 'userpass', 'Ivan', 'Ivanov', 'Ivan Ivanov', 'test@gmail.com', 'userPassword', 'bcrypt', '$2a$10$jKx8N81gvX.r6vP0X4HD.OPHAQtjFwM2aV08ygPaHRdgnpvQuD9JK')


# --- !Downs
DELETE FROM users