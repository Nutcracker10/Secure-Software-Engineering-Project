DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS User;

CREATE TABLE User(
    userId INT AUTO_INCREMENT PRIMARY KEY,
    firstName varchar(300) NOT NULL,
    lastName varchar(300) NOT NULL,
    email varchar(300) NOT NULL,
    role varchar(100) NOT NULL
);

INSERT INTO User(firstName, lastName, email, role) VALUES
    ('test', 'test', 'test@test', 'guest');


CREATE TABLE Reservation()(
    reservationId AUTO_INCREMENT NOT NULL,
    userId INT NOT NULL,
    PRIMARY KEY (reservationId),
    FOREIGN KEY (userId) REFERENCES User (userId)
)