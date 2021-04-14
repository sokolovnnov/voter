DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM votes;
DELETE FROM restaurants;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 1000;


INSERT INTO public.restaurants (id, name) VALUES (10000, 'Ресторан10_000');
INSERT INTO public.restaurants (id, name) VALUES (10001, 'Ресторан10_001');


INSERT INTO public.meals (id, restaurant_id, name, price) VALUES (1003, 10000, 'Чай10_000', 1);
INSERT INTO public.meals (id, restaurant_id, name, price) VALUES (1004, 10000, 'Каша10_000', 2);
INSERT INTO public.meals (id, restaurant_id, name, price) VALUES (1005, 10000, 'Бутерброд10_000', 3);
INSERT INTO public.meals (id, restaurant_id, name, price) VALUES (1006, 10001, 'Кофе10_001', 11);
INSERT INTO public.meals (id, restaurant_id, name, price) VALUES (1007, 10001, 'Плов10_001', 22);
INSERT INTO public.meals (id, restaurant_id, name, price) VALUES (1008, 10001, 'Конфета10_001', 33);