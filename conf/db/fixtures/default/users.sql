# --- !Ups
INSERT INTO users (id, email, password, firstname, lastname) VALUES (1, 'bob@laiboonh.com', 'password', 'Bob', 'Lai');

# --- !Downs
DELETE FROM users;