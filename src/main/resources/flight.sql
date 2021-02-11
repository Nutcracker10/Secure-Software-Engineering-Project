CREATE TABLE flights (
  flight_id INT AUTO_INCREMENT PRIMARY KEY,
  departure DATETIME NOT NULL,
  arrival DATETIME NOT NULL,
  depAirport varchar(100) NOT NULL,
  arrAirport varchar(100) NOT NULL,
  capacity INT NOT NULL,
  price DOUBLE NOT NULL
);
