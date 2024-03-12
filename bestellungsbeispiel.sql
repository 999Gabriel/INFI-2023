DELIMITER $$
CREATE PROCEDURE `GetArtikelAnzahl`()
BEGIN
    SELECT COUNT(*) AS anzahl FROM artikel;
END$$
DELIMITER ;
call GetArtikelAnzahl;


 DELIMITER $$
 CREATE PROCEDURE `GetKundenAnzahl`()
 BEGIN
	SELECT COUNT(*) AS kunden FROM kunden;
END$$
DELIMITER ;
	call GetKundenAnzahl;

use cars;

DELIMITER $$
CREATE procedure get_car()
BEGIN
	select marke, modell from autos where preis >= 1000000;
END$$
DELIMITER ;
call get_car();

CREATE table high_price_cars(auto_id int, marke varchar(50), modell varchar(50), preis decimal(10,2), 
beschreibung text, themengebiet int, bild_url varchar(50));

drop table high_price_cars;

DELIMITER $$
CREATE TRIGGER CheckHighPriceCar
AFTER INSERT ON autos
FOR EACH ROW
BEGIN
    IF NEW.preis >= 1000000 THEN
        INSERT INTO high_price_cars VALUES (NEW.marke, NEW.modell, 
        NEW.preis, NEW.preis, NEW.beschreibung, NEW.themengebiet, NEW.bild_url);
    END IF;
    call get_car();
END$$
DELIMITER ;