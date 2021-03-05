CREATE TABLE credentials (
  id INT AUTO_INCREMENT PRIMARY KEY,
  password varchar(300) NOT NULL
);
CREATE TABLE user(
    id INT AUTO_INCREMENT NOT NULL,
    first_name varchar(300) NOT NULL,
    last_name varchar(300) NOT NULL,
    address varchar(300) NOT NULL,
    phone_number varchar(300) NOT NULL,
    email varchar(300) NOT NULL,
    credential_id INT,
    role varchar(100) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (credential_id) REFERENCES credentials(id)
);
CREATE TABLE credit_card(
    credit_card_id INT AUTO_INCREMENT NOT NULL,
    card_type varchar(60) NOT NULL,
    card_number varchar(20) NOT NULL,
    expiry_month varchar(30) NOT NULL,
    expiry_year varchar(30) NOT NULL,
    security_code varchar(10) NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (credit_card_id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE flights (
  id INT AUTO_INCREMENT PRIMARY KEY,
  departure TIMESTAMP NOT NULL,
  arrival TIMESTAMP NOT NULL,
  dep_airport varchar(100) NOT NULL,
  arr_airport varchar(100) NOT NULL,
  capacity INT NOT NULL,
  price DOUBLE NOT NULL
);