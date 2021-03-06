DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;


CREATE SEQUENCE global_seq START WITH 2000;

CREATE TABLE users
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name          VARCHAR                           NOT NULL,
    e_mail        VARCHAR                           NOT NULL,
    password      VARCHAR                           NOT NULL,
    enabled       BOOL                DEFAULT TRUE  NOT NULL,
    date_time_reg TIMESTAMP           DEFAULT now() NOT NULL

);
CREATE UNIQUE INDEX user_unique_email_idx ON users (e_mail);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id   INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
--     creator_id INTEGER NOT NULL,
    name VARCHAR NOT NULL
);

CREATE TABLE meals
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
--     creator_id    INTEGER   NOT NULL,
    date          DATE    NOT NULL,
    restaurant_id INTEGER NOT NULL,
    name          VARCHAR NOT NULL,
    price         INTEGER NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE votes
(
    id            INTEGER   NOT NULL PRIMARY KEY DEFAULT nextval('global_seq'),
    is_active     BOOLEAN,
    date_time     TIMESTAMP NOT NULL             DEFAULT now(),
    user_id       INTEGER   NOT NULL,
    restaurant_id INTEGER   NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) references restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX vote_unique_time_idx ON votes (date_time);


-- CREATE TABLE roles
-- (
--     role    VARCHAR,
--     user_id INTEGER NOT NULL,
--     FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
-- );

