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