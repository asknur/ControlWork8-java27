-- Пароли:
-- user1@example.com → qwe
-- user2@example.com → qwe

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO users (email, password, name, enabled)
VALUES ('user1@example.com', '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2', 'Иван', true),
       ('user2@example.com', '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2', 'Асан', true);

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'user1@example.com'),
        (SELECT id FROM roles WHERE name = 'ROLE_USER')),
       ((SELECT id FROM users WHERE email = 'user2@example.com'),
        (SELECT id FROM roles WHERE name = 'ROLE_ADMIN'));