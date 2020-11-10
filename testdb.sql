DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS note;

CREATE TABLE IF NOT EXISTS user(
user_id INTEGER PRIMARY KEY AUTOINCREMENT,
email varchar(255) unique,
username varchar(30) unique,
password varchar(40));

create table note(
note_id integer primary key autoincrement,
user_iduser integer,
title varchar(50),
body text,
creationDate date,
lastModificationDate date,
FOREIGN KEY(user_iduser) REFERENCES user(iduser));

INSERT INTO user (email, username, password) values ("sromerom@esliceu.net", "sromerom", "ABCD1234");
INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Nota", "Esto es una nota...", time('now'), time('now'));