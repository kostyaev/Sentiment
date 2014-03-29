# --- First database schema

# --- !Ups
INSERT INTO
  users (id, user_id, provider_id, firstname, lastname, fullname, email, auth_method, hasher, password)
VALUES
  (nextval('user_id'), 'virtuozzo', 'userpass', 'Dmitry', 'Kostyaev', 'Dmitry Kostyaev', 'virtuozz.me@gmail.com', 'userPassword', 'bcrypt', '$2a$10$LUM2PhQqkY2fdr4AmLsJsuqfs7i59A5ytx.6ACqpyDnhSY7A3P.YO');


INSERT INTO
  users (id, user_id, provider_id, firstname, lastname, fullname, email, auth_method, hasher, password)
VALUES
  (nextval('user_id'), 'mary', 'userpass', 'Masha', 'Markina', 'Maria Markina', 'markinamaa@gmail.com', 'userPassword', 'bcrypt', '$2a$10$PCU4phkeLaUuVFMx7k957.0q8PkjPoPsNqREWdv94lSM6ovN7hRhy')


# --- !Downs
DELETE FROM users