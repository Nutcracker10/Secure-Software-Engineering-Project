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
    ('first', 'last', 'test@test', '$2a$10$ifQGrQZoHbTJzSQa0HSXwOM5AT3vKf6zGnXMHSixuyFHmVu.5/afy', 'user'); /*pass = pass */

-- RESERVATION DETAILS ------------------------

CREATE TABLE Reservation() (
    reservationId AUTO_INCREMENT NOT NULL,
    userId INT NOT NULL,
    flight_id INT NOT NULL,
    PRIMARY KEY (reservationId),
    FOREIGN KEY (userId) REFERENCES User (userId)
)

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

