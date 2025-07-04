INSERT INTO users(user_id, first_name, preposition, last_name)
VALUES (1, 'Alfred', '', 'Kats'),
       (2, 'Luna', 'van', 'Dieren');

INSERT INTO accounts(account_id, email, password, user_id)
VALUES (1, 'alfred@email.com', 'password', 1),
       (2, 'luna@email.com', 'password', 2);