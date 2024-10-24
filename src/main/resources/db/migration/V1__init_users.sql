CREATE TABLE admins
(
    id UUID NOT NULL,
    CONSTRAINT pk_admins PRIMARY KEY (id)
);

CREATE TABLE persistent_login
(
    id        UUID NOT NULL,
    username  TEXT NOT NULL,
    series    TEXT NOT NULL,
    token     TEXT NOT NULL,
    last_used TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_persistent_login PRIMARY KEY (id)
);

CREATE TABLE students
(
    id               UUID                     NOT NULL,
    belt_level       TEXT,
    birth_date       TIMESTAMP with time zone NOT NULL,
    parent_full_name TEXT                     NOT NULL,
    parent_phone     TEXT                     NOT NULL,
    CONSTRAINT pk_students PRIMARY KEY (id)
);

CREATE TABLE trainers
(
    id UUID NOT NULL,
    CONSTRAINT pk_trainers PRIMARY KEY (id)
);

CREATE TABLE users
(
    id        UUID NOT NULL,
    full_name TEXT NOT NULL,
    login     TEXT NOT NULL,
    email     TEXT NOT NULL,
    phone     TEXT NOT NULL,
    password  TEXT NOT NULL,
    role      TEXT NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE persistent_login
    ADD CONSTRAINT uc_persistent_login_series UNIQUE (series);

ALTER TABLE persistent_login
    ADD CONSTRAINT uc_persistent_login_token UNIQUE (token);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_login UNIQUE (login);

ALTER TABLE users
    ADD CONSTRAINT uc_users_phone UNIQUE (phone);

ALTER TABLE admins
    ADD CONSTRAINT FK_ADMINS_ON_ID FOREIGN KEY (id) REFERENCES users (id);

ALTER TABLE students
    ADD CONSTRAINT FK_STUDENTS_ON_ID FOREIGN KEY (id) REFERENCES users (id);

ALTER TABLE trainers
    ADD CONSTRAINT FK_TRAINERS_ON_ID FOREIGN KEY (id) REFERENCES users (id);
