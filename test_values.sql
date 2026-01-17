INSERT INTO
    users(id, email, password, phone, city, postal_code, street, building_no)
VALUES (
           '7f974669-80be-4b0f-b6a1-15793702b4ea',
           'example@example.com',
           --TrudneHaslo
           '$argon2id$v=19$m=65536,t=2,p=1$yE++Qh1uadYQuJHLqG0AAxHHPjWu17w1ZzF/PASV9/U$k3cFp1pHiS/C8QYOgazWTKSQ4AZ4wH/gB3fXbed5sAA',
           '123456789',
           'Warszawa',
           '12-345',
           'Aleje Jerozolimskie',
           '5A'
       );

INSERT INTO
    shipments(id, sender_id, recipient_phone, size, city, postal_code, street, building_no)
VALUES (
           '939e012f-51af-4ce0-aa80-025374b24b1d',
           '7f974669-80be-4b0f-b6a1-15793702b4ea',
           '123456789',
           'M',
           'Warszawa',
           '12-345',
           'Wojska Polskiego',
           '17'
       );

INSERT INTO
    shipments(id, sender_id, recipient_phone, recipient_id, size, city, postal_code, street, building_no)
VALUES (
           '3e964ad5-0a42-4a9c-a2e3-d68dee678124',
           '7f974669-80be-4b0f-b6a1-15793702b4ea',
           '123456789',
           '7f974669-80be-4b0f-b6a1-15793702b4ea',
           'M',
           'Warszawa',
           '12-345',
           'Aleje Jerozolimskie',
           '5A'
       );