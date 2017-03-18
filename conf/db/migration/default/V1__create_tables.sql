drop table if exists users;
create sequence user_id_seq start with 1;
create table users (
  id integer PRIMARY KEY default nextval('user_id_seq'),
  email varchar(255) not null,
  password varchar(255) not null,
  firstname varchar(255) not null,
  lastname varchar(255) not null
);

insert into users (id, email, password, firstname, lastname) values (1, 'bob@laiboonh.com', 'password', 'Bob', 'Lai');
