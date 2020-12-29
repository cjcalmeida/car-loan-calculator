CREATE SEQUENCE proponent_id_seq;
CREATE TABLE IF NOT EXISTS proponent (
    id INTEGER NOT NULL DEFAULT nextval('proponent_id_seq') PRIMARY KEY,
    identifier VARCHAR(14) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    state VARCHAR(25) NOT NULL,
    city VARCHAR(50) NOT NULL,
    created_at TIMESTAMP
);
ALTER SEQUENCE proponent_id_seq OWNED BY proponent.id;

GRANT INSERT, SELECT, UPDATE, DELETE on proponent to user_consumer;
GRANT USAGE, SELECT ON SEQUENCE proponent_id_seq TO user_consumer;
