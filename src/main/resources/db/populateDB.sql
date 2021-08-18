DELETE
FROM user_roles;
DELETE
FROM meals;
DELETE
FROM votes;
DELETE
FROM restaurants;
DELETE
FROM users;
-- ALTER SEQUENCE global_seq RESTART WITH 2000;

INSERT INTO public.restaurants (id, name)
VALUES (10000, 'Ресторан10_000');
INSERT INTO public.restaurants (id, name)
VALUES (10001, 'Ресторан10_001');

INSERT INTO public.users (id, name, e_mail, password, enabled, date_time_reg)
VALUES (12000, 'user', 'user@jj.ru', 'password', TRUE, '2021-05-18 13:33:18.000000');
INSERT INTO public.users (id, name, e_mail, password, enabled, date_time_reg)
VALUES (11000, 'admin', 'admin@jj.ru', 'password', TRUE, '2021-05-18 13:34:01.000000');

INSERT INTO public.user_roles (user_id, role)
VALUES (11000, 'ADMIN');
INSERT INTO public.user_roles (user_id, role)
VALUES (12000, 'USER');

INSERT INTO public.votes (id, is_active, date_time, user_id, restaurant_id)
VALUES (1005, FALSE,
        '2021-05-21 11:51:17.000000',
        12000, 10001);

INSERT INTO public.votes (id, is_active, date_time, user_id, restaurant_id)
VALUES (1000, TRUE,
        '2021-05-21 11:57:17.000000', 12000, 10001);
INSERT INTO public.votes (id, is_active, date_time, user_id, restaurant_id)
VALUES (1001, TRUE,
        '2021-05-21 11:59:49.000000', 11000, 10000);
INSERT INTO public.votes (id, is_active, date_time, user_id, restaurant_id)
VALUES (1002, TRUE,
        '2021-06-21 11:59:49.000000', 12000, 10000);

INSERT INTO public.meals (id, date, restaurant_id, name, price)
VALUES (1003, '2020-01-30 10:00:00', 10000, 'Чай10_000', 1);
INSERT INTO public.meals (id, date, restaurant_id, name, price)
VALUES (1004, '2020-01-30 10:00:00', 10000, 'Каша10_000', 2);
INSERT INTO public.meals (id, date, restaurant_id, name, price)
VALUES (1005, '2020-01-30 10:00:00', 10000, 'Бутерброд10_000', 3);
INSERT INTO public.meals (id, date, restaurant_id, name, price)
VALUES (1006, '2020-01-30 10:00:00', 10001, 'Кофе10_001', 11);
INSERT INTO public.meals (id, date, restaurant_id, name, price)
VALUES (1007, '2020-01-30 10:00:00', 10001, 'Плов10_001', 22);
INSERT INTO public.meals (id, date, restaurant_id, name, price)
VALUES (1008, '2020-01-30 10:00:00', 10001, 'Конфета10_001', 33);

-- INSERT INTO roles (role, user_id)
-- VALUES ('USER', 100000),
--        ('ADMIN', 100001),
--        ('USER', 100001);