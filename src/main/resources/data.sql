DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS credit_card;
DROP TABLE IF EXISTS flights;

-- USER DETAILS -----------------------------

CREATE TABLE credit_card(
    credit_card_id INT AUTO_INCREMENT NOT NULL,
    card_type varchar(60) NOT NULL,
    card_number varchar(20) NOT NULL,
    expiry_month varchar(30) NOT NULL,
    expiry_year varchar(30) NOT NULL,
    security_code varchar(10) NOT NULL,
    PRIMARY KEY (credit_card_id)
);

INSERT INTO credit_card(card_type, card_number, expiry_month, expiry_year, security_code) VALUES
    ('Visa Debit', '4024007103939509', '05', '2024', '111');

CREATE TABLE user(
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name varchar(300) NOT NULL,
    last_name varchar(300) NOT NULL,
    email varchar(300) NOT NULL,
    password varchar(300) NOT NULL,
    role varchar(100) NOT NULL,
    credit_card_id INT,
    FOREIGN KEY (credit_card_id) REFERENCES credit_card (credit_card_id)
);

INSERT INTO user(first_name, last_name, email, password, role, credit_card_id) VALUES
    ('test', 'test', 't@t', 'password', 'guest', null),
    ('first', 'last', 'test@test', '$2a$10$ifQGrQZoHbTJzSQa0HSXwOM5AT3vKf6zGnXMHSixuyFHmVu.5/afy', 'member', null); /*pass = pass */

-- FLIGHT DETAILS -----------------------------

CREATE TABLE flights (
  flight_id INT AUTO_INCREMENT PRIMARY KEY,
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


-- RESERVATION DETAILS ------------------------

CREATE TABLE Reservation (
    reservation_id INT AUTO_INCREMENT NOT NULL,
    user_id INT NOT NULL,
    flight_id INT NOT NULL,
    PRIMARY KEY (reservation_id),
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (flight_id) REFERENCES flights (flight_id)
);

INSERT INTO Reservation(user_id, flight_id) VALUES
    ( 1, 1);

