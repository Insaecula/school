
CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(255),
    model VARCHAR(255),
    price NUMERIC
);


CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT CHECK (age >= 0),
    has_license BOOLEAN,
    car_id INT,
    CONSTRAINT fk_car FOREIGN KEY (car_id) REFERENCES car (id)
);