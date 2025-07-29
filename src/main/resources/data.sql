INSERT INTO users(avatar, first_name, preposition, last_name)
VALUES ('avatar_of_user_1.png','Tom', '', 'Kats'),
       ('avatar_of_user_2.png','Kitty', 'van', 'Dieren'),
       ('avatar_of_user_3.png','Leo', 'den', 'Purr');

INSERT INTO accounts(email, password, user_id)
VALUES ('tom@email.com', '$2a$12$GiicvYx.vPEZ8a6yHunCP.OjTNrkPV20MtZZjiOynnMQOdKGaeFtO', 1),
       ('kitty@email.com', '$2a$12$GiicvYx.vPEZ8a6yHunCP.OjTNrkPV20MtZZjiOynnMQOdKGaeFtO', 2),
       ('leo@email.com', '$2a$12$GiicvYx.vPEZ8a6yHunCP.OjTNrkPV20MtZZjiOynnMQOdKGaeFtO', 3);


INSERT INTO posts (creator, post_status, start_date, end_date, title, remark, created_at)
VALUES
    (1, 'ACTIVE', '2025-09-06', '2025-09-07', 'Zoekt weekend katten oppas', 'Mittens houdt van tonijn maar haat de stofzuiger.', CURRENT_TIMESTAMP),
    (2, 'ACTIVE', '2025-12-14', '2025-12-20', 'Oppas gezocht voor tijdens ski trip', 'Verstopt zich overdag, maar knuffelt s nachts.', CURRENT_TIMESTAMP),
    (3, 'ACTIVE', '2025-10-01', '2025-10-08', 'Kat voer- en speeltijd gevraagd', 'Opgepast! Hij opent keukenkastjes', CURRENT_TIMESTAMP);


INSERT INTO responses (comment, created_at, applicant, application_origin)
VALUES
    ('Ik ben dat weekend beschikbaar en kom graag met Mittens spelen', CURRENT_TIMESTAMP, '2', '1'),
    ('Dat is goed nieuws, zie ik je dan!', CURRENT_TIMESTAMP, '1', '1');