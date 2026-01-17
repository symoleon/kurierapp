CREATE TYPE shipment_size AS ENUM ('S', 'M', 'L', 'XL');

CREATE TABLE shipments(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    sender_id uuid NOT NULL REFERENCES users(id),
    recipient_id uuid REFERENCES users(id),
    recipient_phone VARCHAR NOT NULL,
    size shipment_size NOT NULL,
    city VARCHAR NOT NULL,
    postal_code VARCHAR NOT NULL,
    street VARCHAR,
    building_no VARCHAR NOT NULL,
    apartment_no VARCHAR
);

CREATE TABLE users(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    city VARCHAR NOT NULL,
    postal_code VARCHAR NOT NULL,
    street VARCHAR,
    building_no VARCHAR NOT NULL,
    apartment_no VARCHAR
)