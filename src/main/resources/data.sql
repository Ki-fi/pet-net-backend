
INSERT INTO users(avatar, first_name, preposition, last_name, role)
VALUES ('avatar_of_user_1.png','Tom', '', 'Kats', 'ADMIN'),
       ('avatar_of_user_2.png','Kitty', 'van', 'Dieren', 'USER'),
       ('avatar_of_user_3.png','Leo', 'den', 'Purr', 'USER');

INSERT INTO accounts(email, password, user_id)
VALUES ('tom@email.com', '$2a$12$GiicvYx.vPEZ8a6yHunCP.OjTNrkPV20MtZZjiOynnMQOdKGaeFtO', 1),
       ('kitty@email.com', '$2a$12$GiicvYx.vPEZ8a6yHunCP.OjTNrkPV20MtZZjiOynnMQOdKGaeFtO', 2),
       ('leo@email.com', '$2a$12$GiicvYx.vPEZ8a6yHunCP.OjTNrkPV20MtZZjiOynnMQOdKGaeFtO', 3);

INSERT INTO posts (creator, post_status, start_date, end_date, title, remark, created_at)
VALUES
    (1, 'ACTIVE', '2025-09-06', '2025-09-07', 'Zoekt weekend katten oppas', 'Mittens houdt van tonijn maar haat de stofzuiger.', CURRENT_TIMESTAMP),
    (2, 'ACTIVE', '2025-12-14', '2025-12-20', 'Oppas gezocht voor tijdens ski trip', 'Zorg dat de waterfontein altijd aan staat, Mijn kat drinkt alleen daaruit', CURRENT_TIMESTAMP),
    (3, 'ACTIVE', '2025-10-01', '2025-10-08', 'Kat voer- en speeltijd gevraagd', 'Opgepast! Hij opent keukenkastjes', CURRENT_TIMESTAMP);

INSERT INTO services (title, description, post_id)
VALUES
    ('Voeren', 'Elke dag een blikje tonijn voeren, bij voorkeur in de ochtend', 1),
    ('Voeren', 'Elke dag een vers kippenhart geven, aanvullende brokjes komen uit de automaat', 2),
    ('Drinkwater verversen', 'Bakje even uitspoelen en vullen met kraanwater', 1),
    ('Kattenbak verschonen', 'Verse contributie uit de kattenbak scheppen', 1),
    ('Spelen', 'Mijn kat doet graag trucjes. Geef hem een commando om uit te voeren en beloon hem daarna met een snoepje', 3),
    ('Vacht borstelen', 'Mijn kat krijgt snel klitten en houdt er erg van om geborsteld te worden', 2),
    ('Foto-updates', 'Af en toe een foto en korte update sturen om mij op de hoogte te houden.', 2),
    ('Instoppen voor het slapen gaan', 'Mijn kat gaat alleen slapen als je hem een verhaaltje hebt voorgelezen en hem hebt ingestopt.', 2);

INSERT INTO responses (comment, created_at, applicant, application_origin)
VALUES
    ('Ik ben dat weekend beschikbaar en kom graag met Mittens spelen', CURRENT_TIMESTAMP, '2', '1'),
    ('Dat is goed nieuws, zie ik je dan!', CURRENT_TIMESTAMP, '1', '1');