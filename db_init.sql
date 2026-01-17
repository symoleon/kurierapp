CREATE TYPE shipment_size AS ENUM ('S', 'M', 'L', 'XL');

CREATE TABLE users(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    city VARCHAR NOT NULL,
    postal_code VARCHAR NOT NULL,
    street VARCHAR,
    building_no VARCHAR NOT NULL,
    apartment_no VARCHAR
);

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


INSERT INTO
    users(id, email, password, city, postal_code, street, building_no)
VALUES (
        '7f974669-80be-4b0f-b6a1-15793702b4ea',
        'example@example.com',
        --TrudneHaslo
        '$argon2id$v=19$m=65536,t=2,p=1$yE++Qh1uadYQuJHLqG0AAxHHPjWu17w1ZzF/PASV9/U$k3cFp1pHiS/C8QYOgazWTKSQ4AZ4wH/gB3fXbed5sAA',
        'Warszawa',
        '12-345',
        'Aleje Jerozolimskie',
        '5A'
       );

INSERT INTO
    shipments(sender_id, recipient_phone, size, city, postal_code, street, building_no)
VALUES (
        '7f974669-80be-4b0f-b6a1-15793702b4ea',
        '123456789',
        'M',
        'Warszawa',
        '12-345',
        'Wojska Polskiego',
        '17'
       );