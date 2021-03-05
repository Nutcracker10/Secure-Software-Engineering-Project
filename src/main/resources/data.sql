
DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS credit_card;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS Credentials;
DROP TABLE IF EXISTS flights;

-- USER DETAILS -----------------------------

CREATE TABLE credentials (
  id INT AUTO_INCREMENT PRIMARY KEY,
  password varchar(300) NOT NULL
);

INSERT INTO credentials(password) VALUES
    ('$2a$10$ifQGrQZoHbTJzSQa0HSXwOM5AT3vKf6zGnXMHSixuyFHmVu.5/afy'); /*pass = pass */



CREATE TABLE user(
    id INT AUTO_INCREMENT NOT NULL,
    first_name varchar(300) NOT NULL,
    last_name varchar(300) NOT NULL,
    address varchar(300) NOT NULL,
    phone_number varchar(300) NOT NULL,
    email varchar(300) NOT NULL,
    credential_id INT,
    role varchar(100) NOT NULL,
    credit_card_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (credential_id) REFERENCES credentials(id)
);

INSERT INTO user(first_name, last_name, address, phone_number, email, credential_id, role, credit_card_id) VALUES
    ('first', 'last', 'ucd', '+3531111111', 'test@test', 1, 'member', null); /*pass = pass */


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

INSERT INTO credit_card(card_type, card_number, expiry_month, expiry_year, security_code, user_id) VALUES
    ('Visa Debit', '4024007103939509', '05', '2024', '111', 1);


-- FLIGHT DETAILS -----------------------------

CREATE TABLE flights (
  id INT AUTO_INCREMENT PRIMARY KEY,
  departure TIMESTAMP NOT NULL,
  arrival TIMESTAMP NOT NULL,
  dep_airport varchar(100) NOT NULL,
  arr_airport varchar(100) NOT NULL,
  capacity INT NOT NULL,
  price DOUBLE NOT NULL
);

INSERT INTO flights( departure, arrival, dep_airport, arr_airport, capacity, price) VALUES
    ('2008-11-11 10:00:00', '2008-11-11 11:00:00', 'Gatwick', 'Dublin', 200, 25.0 );

INSERT INTO flights(departure, arrival, dep_airport, arr_airport, capacity, price) VALUES
    ('2008-11-13 12:30:00', '2008-11-13 14:30:00', 'Madrid', 'Dublin', 200, 30.0  );

INSERT INTO flights(departure, arrival, dep_airport, arr_airport, capacity, price) VALUES
('2021-11-13 12:30:00', '2021-11-13 14:30:00', 'Dublin', 'Berlin', 200, 30.0  );


-- RESERVATION DETAILS ------------------------

CREATE TABLE Reservation (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    flight_id INT NOT NULL,
    user_id INT,
    status VARCHAR(10) NOT NULL,
    first_name varchar(300) NOT NULL,
    last_name varchar(300) NOT NULL,
    home_address varchar(300) NOT NULL,
    phonenumber varchar(300) NOT NULL,
    email varchar(300) NOT NULL,
    FOREIGN KEY (flight_id) REFERENCES flights (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);

INSERT INTO Reservation(flight_id, user_id, first_name, last_name, home_address, phonenumber, email, status) VALUES
    (1, 1, 'Bob', 'Bobson', 'Bob Lane', '0761234332', 'bob@bobmail.com', 'SCHEDULED');

