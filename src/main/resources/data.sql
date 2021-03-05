
INSERT INTO credentials(password) VALUES
    ('$2a$10$ifQGrQZoHbTJzSQa0HSXwOM5AT3vKf6zGnXMHSixuyFHmVu.5/afy'),
    ('$2a$10$ifQGrQZoHbTJzSQa0HSXwOM5AT3vKf6zGnXMHSixuyFHmVu.5/afy'),
    ('$2a$10$ifQGrQZoHbTJzSQa0HSXwOM5AT3vKf6zGnXMHSixuyFHmVu.5/afy'),
    ('$2a$10$ifQGrQZoHbTJzSQa0HSXwOM5AT3vKf6zGnXMHSixuyFHmVu.5/afy'); /*pass = pass */

INSERT INTO user(first_name, last_name, address, phone_number, email, credential_id, role) VALUES
    ('first', 'last', 'ucd', '+3531111111', 'test@test', 1, 'member'),
    ('timoty', 'l', 'programmer', '+1111111', 'a@a', 2, 'member'),
    ('arrr', 'nameee', 'moon', '+123456', 'b@b', 3, 'member'),
    ('asv', 'uyhtg', 'ucd', '+3531111111', 'c@c', 4, 'member'); /*pass = pass */

INSERT INTO credit_card(card_type, card_number, expiry_month, expiry_year, security_code, user_id) VALUES
    ('Visa Debit', '4024007103939509', '5', '2024', '111', 1);

INSERT INTO flights( departure, arrival, dep_airport, arr_airport, capacity, price) VALUES
    ('2008-11-11 10:00:00', '2008-11-11 11:00:00', 'Gatwick', 'Dublin', 200, 25.0 );

INSERT INTO flights(departure, arrival, dep_airport, arr_airport, capacity, price) VALUES
    ('2008-11-13 12:30:00', '2008-11-13 14:30:00', 'Madrid', 'Dublin', 200, 30.0  );

INSERT INTO flights(departure, arrival, dep_airport, arr_airport, capacity, price) VALUES
('2021-11-13 12:30:00', '2021-11-13 14:30:00', 'Dublin', 'Berlin', 200, 30.0);

INSERT INTO reservation(flight_id, user_id,status) VALUES
    ( 1, 1, 'SCHEDULED');

