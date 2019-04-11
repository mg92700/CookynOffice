-- CREATE DATABASE rmf;
-- 
-- USE rmf;
-- DROP TABLE user;
-- DROP TABLE film;
-- DROP TABLE friend;
-- DROP TABLE rank;
CREATE TABLE user
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    pseudo VARCHAR(255),
    url_image VARCHAR(100),
	password VARCHAR(255),
    email  VARCHAR(255),
    nb_follow INT,
    nb_follower INT
);

INSERT INTO user (pseudo, url_image, password, email, nb_follow,nb_follower)
 VALUES
 ('balek', null, 'Password91','antoine@test.fr',0,0),
  ('mouhsin', null, 'Password91','mouhsin@test.fr',0,0);
 
CREATE TABLE film
(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    id_film VARCHAR(50),
    title VARCHAR(255),
    poster VARCHAR(255),
    moyenne DOUBLE,
    nb_rank INT
);

INSERT INTO film(id_film,title,poster, moyenne, nb_rank)
VALUES
('37735','Easy girl','wu4tawMznxUvqP21lUH6RKrv3LQ.jpg',0,0);
 
 CREATE TABLE rank
 (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    id_user INT,
    id_film VARCHAR(50),
    rank INT
 );
 
 INSERT INTO rank(id_user, id_film, rank)
 VALUES
 (1, '37735',3);
 
 
 CREATE TABLE friend
 (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    id_suiveur INT,
    id_suivi INT
 );
 
 INSERT INTO friend(id_suiveur, id_suivi) VALUES
 (1,2);