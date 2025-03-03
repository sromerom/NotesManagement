DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS note;

CREATE TABLE IF NOT EXISTS user(
user_id INTEGER PRIMARY KEY AUTOINCREMENT,
email TEXT UNIQUE NOT NULL,
username TEXT UNIQUE NOT NULL,
password TEXT NOT NULL);

CREATE TABLE IF NOT EXISTS note(
note_id INTEGER PRIMARY KEY AUTOINCREMENT,
user_iduser INTEGER NOT NULL,
title TEXT ,
body TEXT,
creationDate DATETIME,
lastModificationDate DATETIME,
FOREIGN KEY(user_iduser) REFERENCES user(user_id) ON DELETE CASCADE ON UPDATE CASCADE);


CREATE TABLE IF NOT EXISTS sharedNote(
shared_note INTEGER PRIMARY KEY AUTOINCREMENT,
note_id INTEGER NOT NULL,
user_id INTEGER NOT NULL,
FOREIGN KEY(note_id) REFERENCES note(note_id) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE ON UPDATE CASCADE);

//INSERTS


//Notas del usuario sromerom
//INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Nota4", "Esto es una nota4...", datetime('now','localtime'), datetime('now','localtime'));



INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Note example 1", "Body note 1 example", '2020-11-10 12:00:00', '2020-11-10 12:00:00');
INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Note example 2", "Body note 2 example", '2020-11-10 12:00:00', '2020-11-10 12:00:00');

INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Note example 3", "Body note 3 example", '2020-11-11 12:00:00', '2020-11-11 12:00:00');
INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Note example 4", "Body note 4 example", '2020-11-11 12:00:00', '2020-11-11 12:00:00');

INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Note example 5", "Body note 5 example", '2020-11-12 12:00:00', '2020-11-12 12:00:00');
INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Note example 6", "Body note 6 example", '2020-11-12 12:00:00', '2020-11-12 12:00:00');

INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Note example 7", "Body note 7 example", '2020-11-13 12:00:00', '2020-11-13 12:00:00');
INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Note example 8", "Body note 8 example", '2020-11-13 12:00:00', '2020-11-13 12:00:00');

INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Note example 9", "Body note 9 example", '2020-11-14 12:00:00', '2020-11-14 12:00:00');
INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Note example 10", "Body note 10 example", '2020-11-14 12:00:00', '2020-11-14 12:00:00');

INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Note example 11", "Body note 11 example", '2020-11-15 12:00:00', '2020-11-15 12:00:00');
INSERT INTO note (user_iduser, title, body, creationDate, lastModificationDate) values (1, "Note example 12", "Body note 12 example", '2020-11-15 12:00:00', '2020-11-15 12:00:00');

-------------------------------------------------POR HACER...-------------------------------------------------------
//Limitacion de intentos de login
//Acentos sqlite3
//Cambiar el nombre del proyecto
//e.printStackTrace();
//Cambiar string a date del modelo note
//poner tamaño maximo a un nombre de usuario
//poner tamaño maximo a una contraseña de usuario
-------------------------------------------------HECHAS-------------------------------------------------------
//TABLA N-M -->Hecho
//Implementar paginacion para las sharedNote -->Hecho
//Crear detailPageNote --> Hecho
//Implementar markdown --> Hecho
//comprovar si un usuari pot veure, editar o eliminar un nota en concret... ################################### --> Hecho
//cambiar eliminacion de una nota de get por post ################################### --> Hecho
//En delete, sobra cosas... --> Hecho
//Comprobar que nadie pueda crear y eliminar notas compartidas que no sean suyas... ############################ --> Hecho
//Arreglar las opciones que se encuentras en las notas creadas que han sido compartidas ################################ --> Hecho
//Intentar arreglar fallo de diseño (quitar dao, modelo y service de sharedNote...) --> Hecho
//Evitar poder compartirse una nota a si mismo ############################ --> Hecho
//Crear salt aleatorio ################################### --> Hecho
//DeleteShare mal implementado (excepcion) --> Hecho
//Revisar eliminacion de notas compartidas --> Hecho
//Arreglar fallo del login (Error 500) ############################ --> Hecho
//Poder ver el detail de las notas compartidas ######################## --> Hecho
//hacer filtro de las notas creadas que has compartido ################################### --> Hecho
//Hacer diseño presentable ############################## --> Hecho
//Acabar hacer filtros de busqueda ################################# --> Hecho
//Contraseñas fuertes --> Hecho
//implementar csrf ############################# --> Hecho
//logout por post --> Hecho
//Renderizar nota a texto home --> Hecho
//hacer include de el header para no repetir codigo en los jsp --> Hecho
//Utilizar dao user en dao note ############################## --> Hecho de cierta forma. Extracion a un metodo de todas las partes repetidas
//Crear mensajes de error mostrando al usuari que algo mal ha ocurrido... ####################################### -->Hecho
//Crear validaciones ################################### -->Hecho
//Crear mensajes de error mostrando al usuari que algo mal ha ocurrido... ####################################### -->Hecho
//escape script markdown --> Hecho
//Todo al mismo idioma --> Hecho
//variable conn --> Hecho
//testear aplicacion ######################################## --> Hecho
//Arreglar fallo de paginacion, te añade un boton extra ################################### --> Hecho
//Controller comprova si hi ha dades a enviar abans de fer operacions ####################### --> Hecho
//Arreglar welcome de home --> Hecho
//Comprobar eliminacion de share de notas que te comparten --> Hecho
