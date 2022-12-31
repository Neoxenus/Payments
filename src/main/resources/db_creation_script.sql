
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS credit_card_order;
DROP TABLE IF EXISTS credit_card;
DROP TABLE IF EXISTS money_account;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS account;
DROP table if exists users;
CREATE TYPE role_type AS ENUM ('GUEST', 'USER', 'ADMIN');

CREATE TABLE users
(
    id          SERIAL PRIMARY KEY,
    userName    VARCHAR(60),
    phoneNumber BIGINT UNIQUE,
    role        role_type,
    email       VARCHAR(50),
    password    varchar(100)
);

CREATE TABLE account
(
     id SERIAL PRIMARY KEY,
     number VARCHAR UNIQUE,
     IBAN VARCHAR(45),
     dateOfRegistration DATE,

     userId INT REFERENCES users on delete cascade
);

CREATE TABLE IF NOT EXISTS credit_card
(
    id                SERIAL PRIMARY KEY,
    number            BIGINT,
    cvv               INT,
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


INSERT INTO users VALUES(DEFAULT, 'Volodymyr Kyryliuk', '1234567890', 'ADMIN', 'kirilyukvolodimir2003@gmail.com',
                           '1111');
INSERT INTO account VALUES(DEFAULT, '0003', 'UA10003', '2021-08-24', 1);
