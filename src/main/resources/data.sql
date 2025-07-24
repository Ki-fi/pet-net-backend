INSERT INTO users(first_name, preposition, last_name)
VALUES ('Tom', '', 'Kats'),
       ('Kitty', 'van', 'Dieren'),
       ('Leo', 'den', 'Purr');

INSERT INTO accounts(email, password, user_id)
VALUES ('tom@email.com', '$2a$12$GiicvYx.vPEZ8a6yHunCP.OjTNrkPV20MtZZjiOynnMQOdKGaeFtO', 1),
       ('merel@email.com', '$2a$12$GiicvYx.vPEZ8a6yHunCP.OjTNrkPV20MtZZjiOynnMQOdKGaeFtO', 2),
       ('leo@email.com', '$2a$12$GiicvYx.vPEZ8a6yHunCP.OjTNrkPV20MtZZjiOynnMQOdKGaeFtO', 3);


INSERT INTO posts (creator, post_status, start_date, end_date, title, remark, created_at)
VALUES
    (1, 'ACTIVE', '2025-01-05', '2025-01-10', 'Zoekt weekend katten oppas', 'Mittens houdt van tonijn maar haat de stofzuiger.', CURRENT_TIMESTAMP),
    (2, 'ACTIVE', '2025-02-14', '2025-02-20', 'Oppas gezocht voor tijdens ski trip', 'Verstopt zich overdag, maar knuffelt s nachts.', CURRENT_TIMESTAMP),
    (3, 'ACTIVE', '2025-03-01', '2025-03-08', 'Kat voer- en speeltijd gevraagd', 'Opgepast! Hij opent keukenkastjes', CURRENT_TIMESTAMP);