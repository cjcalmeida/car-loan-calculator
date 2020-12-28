CREATE TABLE IF NOT EXISTS vehicle (
    id UUID NOT NULL PRIMARY KEY,
    market_value DECIMAL(10, 2) NOT NULL
);

CREATE SEQUENCE location_id_seq;
CREATE TABLE IF NOT EXISTS location (
  id INTEGER NOT NULL DEFAULT nextval('location_id_seq') PRIMARY KEY,
  city VARCHAR(25) NOT NULL,
  state VARCHAR(5) NOT NULL
);
ALTER SEQUENCE location_id_seq OWNED BY location.id;

CREATE SEQUENCE proponent_id_seq;
CREATE TABLE IF NOT EXISTS proponent (
    id INTEGER NOT NULL DEFAULT nextval('proponent_id_seq') PRIMARY KEY,
    identity VARCHAR(14) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL
);
ALTER SEQUENCE proponent_id_seq OWNED BY proponent.id;

CREATE SEQUENCE simulation_id_seq;
CREATE TABLE IF NOT EXISTS simulation (
    id INTEGER NOT NULL DEFAULT nextval('simulation_id_seq') PRIMARY KEY,
    public_id UUID NOT NULL UNIQUE,
    created_at TIMESTAMP,
    down_payment DECIMAL(10, 2) NOT NULL,
    installment_value DECIMAL(10, 2) NOT NULL,
    payment_term INTEGER NOT NULL,
    tax_values DECIMAL(10, 6) NOT NULL,
    vehicle_value DECIMAL(10, 2) NOT NULL,
    location_id INTEGER NOT NULL,
    proponent_id INTEGER NOT NULL,
    vehicle_id UUID NOT NULL,
    FOREIGN KEY (location_id) REFERENCES location(id),
    FOREIGN KEY (proponent_id) REFERENCES proponent(id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicle(id)
);
ALTER SEQUENCE simulation_id_seq OWNED BY simulation.id;


GRANT INSERT, SELECT, UPDATE, DELETE on simulation, proponent, location, vehicle to user_proposal;
GRANT USAGE, SELECT ON SEQUENCE simulation_id_seq, location_id_seq, proponent_id_seq TO user_proposal;