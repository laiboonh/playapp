# --- !Ups
CREATE SEQUENCE user_id_seq;
CREATE TABLE "users" (
  id bigserial PRIMARY KEY default nextval('user_id_seq'),
  email VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  firstname VARCHAR NOT NULL,
  lastname VARCHAR NOT NULL
);


# --- !Downs
DROP SEQUENCE "user_id_seq";
DROP TABLE "users";