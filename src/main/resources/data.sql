DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS flights;

-- USER DETAILS -----------------------------

CREATE TABLE users(
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name varchar(300) NOT NULL,
    last_name varchar(300) NOT NULL,
    email varchar(300) NOT NULL,
    password varchar(300) NOT NULL,
    role varchar(100) NOT NULL
);

INSERT INTO users(first_name, last_name, email, password, role) VALUES
    ('test', 'test', 't@t', 'password', 'guest'),
    ('first', 'last', 'test@test', '$2a$10$ifQGrQZoHbTJzSQa0HSXwOM5AT3vKf6zGnXMHSixuyFHmVu.5/afy', 'member'); /*pass = pass */

-- FLIGHT DETAILS -----------------------------

CREATE TABLE flights (
  flight_id INT AUTO_INCREMENT PRIMARY KEY,
  departure DATETIME NOT NULL,
  arrival DATETIME NOT NULL,
  depAirport varchar(100) NOT NULL,
  arrAirport varchar(100) NOT NULL,
  capacity INT NOT NULL,
  price DOUBLE NOT NULL
);

INSERT INTO flights( departure, arrival, depAirport, arrAirport, capacity, price) VALUES
    ('2008-11-11', '2008-11-11', 'Gatwick', 'Dublin', 200, 25.0 );

-- RESERVATION DETAILS ------------------------

CREATE TABLE Reservation (
    reservation_id INT AUTO_INCREMENT NOT NULL,
    user_id INT NOT NULL,
    flight_id INT NOT NULL,
    PRIMARY KEY (reservation_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (flight_id) REFERENCES flights (flight_id)
);

INSERT INTO Reservation(user_id, flight_id) VALUES
    ( 1, 1);