-- Brand
INSERT INTO vehicles.brand(public_id, name, short_description)
VALUES('33095e5d527b4fad833b9276f9a36131', 'FIAT', 'FIAT is an Italian automobile manufacturer');

INSERT INTO vehicles.brand(public_id, name, short_description)
VALUES('034a937d65494527935b7a6f6431021c', 'FORD', 'Ford Motor Company, commonly known as Ford, is an American multinational automaker');

INSERT INTO vehicles.brand(public_id, name, short_description)
VALUES('7265276f3dab4840bbcfba385db6676d', 'JEEP', 'Jeep is a brand of American automobile and also a division of FCA US LLC');

-- Models

INSERT INTO vehicles.model(public_id, brand_id, name, image)
VALUES('33095e5d527b4fad833b9276f9a36131', 1, '500C', 'https://www.cstatic-images.com/car-pictures/xl/cac20fic021a021001.png');

INSERT INTO vehicles.model(public_id, brand_id, name, image)
VALUES('034a937d65494527935b7a6f6431021c', 2, 'NEW FIESTA', 'https://www.tragial.com.br/wp-content/uploads/2020/02/novo-fiesta.png');

INSERT INTO vehicles.model(public_id, brand_id, name, image)
VALUES('7265276f3dab4840bbcfba385db6676d', 3, 'JEEP RENEGADE', 'https://www.jeep.pt/content/dam/jeep/crossmarket/new-renegade-2019/Overview/02_colorizer/01_Trims/sport/monocolor/Sport_503.png');

-- Versions

INSERT INTO vehicles.version(public_id, model_id, name, value)
VALUES('33095e5d527b4fad833b9276f9a36131', 1, '1.6 Cobrio 16v', 55000.00);

INSERT INTO vehicles.version(public_id, model_id, name, value)
VALUES('034a937d65494527935b7a6f6431021c', 2, '1.5 16V', 35500.00);

INSERT INTO vehicles.version(public_id, model_id, name, value)
VALUES('7265276f3dab4840bbcfba385db6676d', 3, '2.0 Longitude', 80500.00);
