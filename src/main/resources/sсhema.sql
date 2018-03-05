/*
DROP TABLE logins;
DROP TABLE documents;
DROP TABLE countries;
DROP TABLE users;
DROP TABLE addresses;
DROP TABLE organisations;
DROP TABLE offices;
*/


CREATE TABLE IF NOT EXISTS documents (
	code INTEGER PRIMARY KEY,
	doc_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS countries (
	code INTEGER PRIMARY KEY,
	country_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS organisations (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	full_name VARCHAR(255),
	inn BIGINT,
	kpp BIGINT,
	address VARCHAR(255),
	phone BIGINT,
	is_active BOOLEAN
);

CREATE TABLE IF NOT EXISTS offices (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	org_id INTEGER NOT NULL,
	name VARCHAR(255) NOT NULL,
	address VARCHAR(255),
	phone bigint,
	is_active boolean,
	FOREIGN KEY (org_id) REFERENCES organisations(id) 
	ON DELETE CASCADE
	ON UPDATE CASCADE	
);

CREATE TABLE IF NOT EXISTS users (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	office_id INTEGER NOT NULL,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	middle_name VARCHAR(50),
	position VARCHAR(50),
	phone BIGINT,
  doc_code INTEGER NOT NULL,
	doc_number BIGINT NOT NULL,
	doc_date DATE NOT NULL,
	citizenship_code INTEGER NOT NULL,
	is_identified BOOLEAN,
	FOREIGN KEY (doc_code) REFERENCES documents(code),
	FOREIGN KEY (citizenship_code) REFERENCES countries(code),
	FOREIGN KEY (office_id) REFERENCES offices(id) 
	ON DELETE CASCADE
	ON UPDATE CASCADE	
);

CREATE TABLE IF NOT EXISTS logins (
	id integer primary key auto_increment,
	name varchar(50),
	login varchar(50) not null,
	password varchar(50) not null
);