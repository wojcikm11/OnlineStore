use online_store;

DROP TABLE IF EXISTS opinion;
DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS user_info;
DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER'
);

CREATE TABLE IF NOT EXISTS user_info (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL UNIQUE,
    first_name VARCHAR(30),
    last_name VARCHAR(30),
    city VARCHAR(40),
    email VARCHAR(50),
    phone VARCHAR(15),
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS category (
	id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(25) NOT NULL UNIQUE,
    description VARCHAR(1000) NOT NULL
);

CREATE TABLE IF NOT EXISTS product (
	id INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT NULL,
    user_id INT NOT NULL,
    title VARCHAR(25) NOT NULL,
    price FLOAT NOT NULL,
    photo LONGBLOB,
    FOREIGN KEY (category_id) REFERENCES category (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS wishlist (
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (user_id, product_id),
	FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE IF NOT EXISTS opinion (
	id INT PRIMARY KEY AUTO_INCREMENT,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    date_added DATETIME NOT NULL,
    rating INT NOT NULL,
    description VARCHAR(250) NOT NULL,
	FOREIGN KEY (sender_id) REFERENCES user (id),
	FOREIGN KEY (receiver_id) REFERENCES user (id)
);