# --- !Ups
CREATE TABLE "user" (
  id bigserial PRIMARY KEY,
  email VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  firstname VARCHAR NOT NULL,
  lastname VARCHAR NOT NULL
);


# --- !Downs
DROP TABLE "user";