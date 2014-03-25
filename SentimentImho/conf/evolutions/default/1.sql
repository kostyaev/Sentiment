# --- First database schema

# --- !Ups
create sequence user_id;
create sequence opinion_id;
create sequence checked_opinion_id;

create table users (
  id bigint DEFAULT nextval('user_id'),
  user_id VARCHAR(255) NOT NULL,
  provider_id VARCHAR(255) NOT NULL,
  firstname VARCHAR(255) NOT NULL,
  lastname VARCHAR(255) NOT NULL,
  fullname VARCHAR(255) NOT NULL,
  email VARCHAR(255),
  avatar_url VARCHAR(255),
  auth_method VARCHAR(255) NOT NULL,
  token VARCHAR(255),
  secret VARCHAR(255),
  access_token VARCHAR(255),
  token_type VARCHAR(255),
  expires_in INTEGER,
  refresh_token VARCHAR(255),
  hasher VARCHAR(255),
  password VARCHAR(255),
  salt VARCHAR(255),
  PRIMARY KEY (id)
);

create table token (
  uuid VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  creation_time TIMESTAMP NOT NULL,
  expiration_time TIMESTAMP NOT NULL,
  is_signup BOOLEAN NOT NULL
);


create table opinion (
  id BIGINT DEFAULT nextval('opinion_id'),
  message TEXT NOT NULL,
  rating SMALLINT,
  is_checked BOOLEAN,
  PRIMARY KEY (id)
);

create table checked_opinion (
  id BIGINT DEFAULT nextval('checked_opinion_id'),
  message text NOT NULL,
  rating SMALLINT,
  sent_grade SMALLINT,
  opinion_id BIGINT,
  user_id BIGINT,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (opinion_id) REFERENCES opinion(id)
);



# --- !Downs
drop table token;
drop table users;
drop table opinion;
drop table checked_opinion;
drop sequence user_id;
drop sequence opinion_id;
drop sequence checked_opinion_id;