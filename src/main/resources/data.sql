INSERT INTO user(first_name, last_name, username, address, phone_number, email, password) VALUES
('first', 'last', 'first', 'ucd', '+3531111111', 'test@test', '$2a$10$ifQGrQZoHbTJzSQa0HSXwOM5AT3vKf6zGnXMHSixuyFHmVu.5/afy'),
('timoty', 'l', 'timoty', 'programmer', '+1111111', 'a@a', '$2a$10$ifQGrQZoHbTJzSQa0HSXwOM5AT3vKf6zGnXMHSixuyFHmVu.5/afy'),
('arrr', 'nameee', 'arrr', 'moon', '+123456', 'b@b', '$2a$10$ifQGrQZoHbTJzSQa0HSXwOM5AT3vKf6zGnXMHSixuyFHmVu.5/afy'),
('asv', 'uyhtg', 'ucd', 'first', '+3531111111', 'c@c', '$2a$10$ifQGrQZoHbTJzSQa0HSXwOM5AT3vKf6zGnXMHSixuyFHmVu.5/afy'); /*pass = pass */

INSERT INTO role (name) VALUES
('USER'),
('ADMIN');

INSERT INTO credit_card(card_type, card_number, expiry_month, expiry_year, security_code, user_id) VALUES
    ('Visa Debit', '4024007103939509', '5', '2024', '111', 1);


INSERT INTO flight( departure, arrival, dep_airport, arr_airport, capacity, price) VALUES
    ('2008-11-11 10:00:00', '2008-11-11 11:00:00', 'Gatwick', 'Dublin', 200, 25.0 ),
    ('2008-11-13 12:30:00', '2008-11-13 14:30:00', 'Madrid', 'Dublin', 200, 30.0  ),
    ('2021-11-13 12:30:00', '2021-11-13 14:30:00', 'Dublin', 'Berlin', 200, 30.0);

