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
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    flight_id INT NOT NULL,
    first_name varchar(300) NOT NULL,
    last_name varchar(300) NOT NULL,
    home_address varchar(300) NOT NULL,
    email varchar(300) NOT NULL,
    FOREIGN KEY (flight_id) REFERENCES flights (flight_id)
);

INSERT INTO Reservation(user_id, flight_id) VALUES
    ( 1, 1);