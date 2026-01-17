CREATE TYPE shipment_size AS ENUM ('S', 'M', 'L', 'XL');

CREATE TABLE shipments(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    recipient_phone VARCHAR NOT NULL,
    size shipment_size NOT NULL,
    city VARCHAR NOT NULL,
    postal_code VARCHAR NOT NULL,
    street VARCHAR,
    building_no VARCHAR NOT NULL,
    apartment_no VARCHAR
)