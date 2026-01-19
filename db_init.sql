CREATE TYPE shipment_size AS ENUM ('S', 'M', 'L', 'XL');

CREATE TABLE users
(
    id           uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name         VARCHAR NOT NULL,
    email        VARCHAR NOT NULL UNIQUE,
    password     VARCHAR NOT NULL,
    phone        VARCHAR NOT NULL UNIQUE,
    city         VARCHAR NOT NULL,
    postal_code  VARCHAR NOT NULL,
    street       VARCHAR,
    building_no  VARCHAR NOT NULL,
    apartment_no VARCHAR
);

CREATE TABLE shipments
(
    id              uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    sender_id       uuid          NOT NULL REFERENCES users (id),
    recipient_id    uuid REFERENCES users (id),
    recipient_phone VARCHAR       NOT NULL,
    size            shipment_size NOT NULL,
    name            VARCHAR       NOT NULL,
    city            VARCHAR       NOT NULL,
    postal_code     VARCHAR       NOT NULL,
    street          VARCHAR,
    building_no     VARCHAR       NOT NULL,
    apartment_no    VARCHAR
);