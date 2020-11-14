DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS note;

CREATE TABLE IF NOT EXISTS user(
user_id INTEGER PRIMARY KEY AUTOINCREMENT,
email VARCHAR(255) UNIQUE NOT NULL,
username VARCHAR(30) UNIQUE NOT NULL,
password VARCHAR(40) NOT NULL);

CREATE TABLE IF NOT EXISTS note(
note_id INTEGER PRIMARY KEY AUTOINCREMENT,
user_iduser INTEGER NOT NULL,
title VARCHAR(150),
body TEXT,
creationDate DATETIME,
lastModificationDate DATETIME,
FOREIGN KEY(user_iduser) REFERENCES user(iduser));


CREATE TABLE IF NOT EXISTS sharedNote(
shared_note INTEGER PRIMARY KEY AUTOINCREMENT,
note_id INTEGER NOT NULL,
user_id INTEGER NOT NULL,
FOREIGN KEY(note_id) REFERENCES note(note_id) ON DELETE CASCADE,
FOREIGN KEY(user_id) REFERENCES user(iduser) ON DELETE CASCADE);

//INSERTS
INSERT INTO user (email, username, password) values ("sromerom@esliceu.net", "sromerom", "ABCD1234");

//Notas del usuario sromerom
INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Nota", "Esto es una nota...", datetime('now','localtime'), datetime('now','localtime'));
INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Nota2", "Esto es una nota2...", datetime('now','localtime'), datetime('now','localtime'));
INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Nota3", "Esto es una nota3...", datetime('now','localtime'), datetime('now','localtime'));
INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Nota4", "Esto es una nota4...", datetime('now','localtime'), datetime('now','localtime'));

//TABLA N-M -->Hecho
//TABLA RELACIONAL ENTRE NOTAS
//Limitacion de intentos de login
//Contraseñas fuertes
//Crear validaciones
//Crear salt aleatorio
//Todo al mismo idioma
//variable conn
//Implementar paginacion para las sharedNote
//Crear detailPageNote
//Implementar markdown
//cambiar eliminacion de una nota de get por post
//comprovar si un usuari pot veure o eliminar un nota en concret...
//Acentos sqlite3
//Controller comprova si hi ha dades a enviar abans de fer operacions
<<<<<<< HEAD
//DeleteShare mal implementado --> excepcion
=======
>>>>>>> 1daa1b8c848f10e0094a21fbdcde1935a93d3810
