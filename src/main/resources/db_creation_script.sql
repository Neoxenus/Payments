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
    IBAN                 VARCHAR(10),
    date_of_registration TIMESTAMP,
    balance_amount       DOUBLE PRECISION,
    is_blocked           BOOLEAN,
    user_id              INT REFERENCES users on delete cascade
);

CREATE TABLE IF NOT EXISTS credit_card
(
    id                SERIAL PRIMARY KEY,
    number            VARCHAR(12) UNIQUE,
    cvv               VARCHAR(3),
    expire_date       VARCHAR(7),
    account_id        INT REFERENCES account on delete cascade
);


CREATE TYPE payment_status_type AS ENUM ('PREPARED', 'SENT');

CREATE TABLE IF NOT EXISTS payment
(
    number                  INT PRIMARY KEY,
    amount                  DOUBLE PRECISION,
    assignment              VARCHAR(100),
    time                    TIMESTAMP,
    status                  payment_status_type,
    sender_account_id INT REFERENCES account on delete cascade,
    receiver_account_id INT REFERENCES account on delete cascade
);

------------------
INSERT INTO users VALUES(1, 'Volodymyr Kyryliuk', 'kirilyukvolodimir2003@gmail.com', '1234567890', 'ADMIN',
                           '1111', FALSE);
INSERT INTO account VALUES(1, '0001', 'Volodymyr Kyryliuk', 'UA0001', '2021-08-24 10:23:54', 1000.00, FALSE, 1);
------------------


INSERT INTO users VALUES(2, 'Petro Petrov', 'petrov@gmail.com', '1234567891', 'USER',
                         '1111', FALSE);
INSERT INTO account VALUES(2, '0010', 'Ivan Petrov', 'UA0010', '2022-08-24 10:23:54', 2000.00, FALSE, 2);
INSERT INTO account VALUES(3, '0011', 'Petro Petrov', 'UA0011', '2022-09-24 10:24:54', 1000.00, FALSE, 2);
INSERT INTO credit_card VALUES(1, 'CC0011', '111', '2023-03', 3);
----------------------