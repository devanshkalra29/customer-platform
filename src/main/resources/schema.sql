CREATE TABLE IF NOT EXISTS customer (
    id UUID PRIMARY KEY,
    prefix VARCHAR(50),
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    last_name VARCHAR(255) NOT NULL,
    suffix VARCHAR(50),
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50)
);