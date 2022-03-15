CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name CHAR(50) NOT NULL,
    price NUMERIC NOT NULL,
    date DATE NOT NULL
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email CHAR(50) NOT NULL,
    password CHAR(150) NOT NULL,
    salt CHAR(100) NOT NULL,
    role CHAR(50) DEFAULT 'user',
    date DATE NOT NULL
);

INSERT INTO users VALUES (1, 'admin@gmail.com', '0006217a1398c3cd6f88618a7e215f266cd2d9f68cab01d9158cbefbe533f215',
                          '8879c983-c245-47ae-aba0-7181a1a13253', 'admin', '2022-03-15');
