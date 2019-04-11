CREATE DATABASE ydays;

USE ydays;

CREATE TABLE user
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    login VARCHAR(100),
    password VARCHAR(100),
    ip_centreon VARCHAR(100),
    login_centreon VARCHAR(255),
    password_centreon VARCHAR(255),
    token_phone_id VARCHAR(511)
   
);

INSERT INTO user (login, password, ip_centreon, login_centreon, password_centreon,token_phone_id)
 VALUES
 ('ydays', 'Ydays2018', '188.166.65.62','admin','Ydays2018','android11111' );

 