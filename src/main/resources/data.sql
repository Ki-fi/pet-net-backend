INSERT INTO users(user_id, first_name, preposition, last_name)
VALUES (1, 'Tom', '', 'Kats'),
       (2, 'Merel', 'van', 'Dieren'),
       (3, 'Leo', 'den', 'Purr');

INSERT INTO accounts(account_id, email, password, user_id)
VALUES (1, 'tom@email.com', 'password', 1),
       (2, 'merel@email.com', 'password', 2),
       (3, 'leo@email.com', 'password', 3);


INSERT INTO posts (post_id, creator, post_status, start_date, end_date, title, remark, created_at)
VALUES
    (1, 1, 'ACTIVE', '2025-01-05', '2025-01-10', 'Looking for weekend cat care', 'Mittens loves tuna and hates the vacuum cleaner.', CURRENT_TIMESTAMP),
    (2, 2, 'ACTIVE', '2025-02-14', '2025-02-20', 'Need sitter while on ski trip', 'Snuggles at night, hides during the day.', CURRENT_TIMESTAMP),
    (3, 3, 'ACTIVE', '2025-03-01', '2025-03-08', 'Cat feeding and playtime needed', 'Watch out, he opens cupboard doors.', CURRENT_TIMESTAMP);