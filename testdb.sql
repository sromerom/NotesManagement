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
title varchar(150),
body text,
creationDate datetime,
lastModificationDate datetime,
FOREIGN KEY(user_iduser) REFERENCES user(iduser));

INSERT INTO user (email, username, password) values ("sromerom@esliceu.net", "sromerom", "ABCD1234");

//Notas del usuario sromerom
INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Nota", "Esto es una nota...", datetime('now','localtime'), datetime('now','localtime'));
INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Nota2", "Esto es una nota2...", datetime('now','localtime'), datetime('now','localtime'));
INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Nota3", "Esto es una nota3...", datetime('now','localtime'), datetime('now','localtime'));
INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Nota4", "Esto es una nota4...", datetime('now','localtime'), datetime('now','localtime'));

//TABLA N-M
//TABLA RELACIONAL ENTRE NOTAS

