INSERT INTO users(user_id, first_name, preposition, last_name)
VALUES (1, 'Tom', '', 'Kats'),
       (2, 'Kitty', 'van', 'Dieren'),
       (3, 'Leo', 'den', 'Purr');

INSERT INTO accounts(account_id, email, password, user_id)
VALUES (1, 'tom@email.com', 'password', 1),
       (2, 'merel@email.com', 'password', 2),
       (3, 'leo@email.com', 'password', 3);


INSERT INTO posts (post_id, creator, post_status, start_date, end_date, title, remark, created_at)
VALUES
    (1, 1, 'ACTIVE', '2025-01-05', '2025-01-10', 'Zoekt weekend katten oppas', 'Mittens houdt van tonijn maar haat de stofzuiger.', CURRENT_TIMESTAMP),
    (2, 2, 'ACTIVE', '2025-02-14', '2025-02-20', 'Oppas gezocht voor tijdens ski trip', 'Verstopt zich overdag, maar knuffelt s nachts.', CURRENT_TIMESTAMP),
    (3, 3, 'ACTIVE', '2025-03-01', '2025-03-08', 'Kat voer- en speeltijd gevraagd', 'Opgepast! Hij opent keukenkastjes', CURRENT_TIMESTAMP);