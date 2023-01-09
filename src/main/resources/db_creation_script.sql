SET lc_messages TO 'en_US.UTF-8';
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS credit_card_order;
DROP TABLE IF EXISTS credit_card;
DROP TABLE IF EXISTS money_account;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS account;
DROP table if exists users;
drop type IF exists role_type;
drop type if exists payment_status_type;
drop type if exists payment_status_type;

CREATE TYPE role_type AS ENUM ('GUEST', 'USER', 'ADMIN');

CREATE TABLE users
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(30),
    email        VARCHAR(50) UNIQUE,
    phone_number VARCHAR(15),
    role         role_type,
    password     varchar(100),
    is_blocked   BOOLEAN
);

CREATE TABLE account
(
    id                   SERIAL PRIMARY KEY,
    number               VARCHAR UNIQUE,
    account_name         VARCHAR,
    IBAN                 VARCHAR(45),
    date_of_registration DATE,
    balance_amount       DOUBLE PRECISION,
    is_blocked           BOOLEAN,
    user_id              INT REFERENCES users on delete cascade
);

CREATE TABLE IF NOT EXISTS credit_card
(
    id                SERIAL PRIMARY KEY,
    number            VARCHAR(16) UNIQUE,
    cvv               VARCHAR(3),
    expire_date       VARCHAR(10),
    account_id        INT REFERENCES account on delete cascade
);


CREATE TYPE payment_status_type AS ENUM ('PREPARED', 'SENT');

CREATE TABLE IF NOT EXISTS payment
(
    number                  INT PRIMARY KEY,
    amount                  DOUBLE PRECISION,
    assignment              VARCHAR(100),
    time                    DATE,
    status                  payment_status_type,
    sender_account_id INT REFERENCES account on delete cascade,
    receiver_account_id INT REFERENCES account on delete cascade
);


INSERT INTO users VALUES(DEFAULT, 'Volodymyr Kyryliuk', 'kirilyukvolodimir2003@gmail.com', '1234567890', 'ADMIN',
                           '1111', FALSE);
INSERT INTO account VALUES(DEFAULT, '0003', 'Volodymyr Kyryliuk', 'UA10003', '2021-08-24', 1000.0, FALSE, 1);
