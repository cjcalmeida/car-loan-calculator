CREATE SEQUENCE template_id_seq;
CREATE TABLE IF NOT EXISTS template(
    id INTEGER NOT NULL DEFAULT nextval('template_id_seq') PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE,
    file VARCHAR(50) NOT NULL,
    resources VARCHAR
);
ALTER SEQUENCE template_id_seq OWNED BY template.id;

CREATE SEQUENCE email_id_seq;
CREATE TABLE IF NOT EXISTS email(
    id INTEGER NOT NULL DEFAULT nextval('email_id_seq') PRIMARY KEY,
    public_id UUID NOT NULL UNIQUE,
    subject VARCHAR(50) NOT NULL,
    destination VARCHAR(50) NOT NULL,
    sent_date TIMESTAMP,
    status VARCHAR NOT NULL,
    template_id INTEGER NOT NULL,
    FOREIGN KEY (template_id) REFERENCES email(id)
);
ALTER SEQUENCE email_id_seq OWNED BY email.id;

GRANT INSERT, SELECT, UPDATE, DELETE on template, email to user_notification;
GRANT USAGE, SELECT ON SEQUENCE template_id_seq, email_id_seq TO user_notification;