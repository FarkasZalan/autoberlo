CREATE TABLE  IF NOT EXISTS auto (
    auto_id INT(10) NOT NULL AUTO_INCREMENT,
    auto_rendszam VARCHAR(128) NOT NULL,
    auto_nev VARCHAR(128) NOT NULL,
     marka VARCHAR(128) NOT NULL,
      tipus VARCHAR(128) NOT NULL,
       motor_kobcenti INT(10) NOT NULL,
       evjarat INT(10) NOT NULL,
        ulesek_szama INT(10) NOT NULL,
        berles_dija INT(10) NOT NULL,
        kep MEDIUMBLOB NOT NULL,
    PRIMARY KEY (auto_id)
);


CREATE TABLE  IF NOT EXISTS foglalasok (
    nyugta_id INT(10) NOT NULL AUTO_INCREMENT,
    foglalo_email VARCHAR(128) NOT NULL,
    foglalo_cim VARCHAR(128) NOT NULL,
    foglalo_telefonszam VARCHAR(128) NOT NULL,
      fizetendo_osszeg INT(10) NOT NULL,
       foglalando_auto_rendszam VARCHAR(128) NOT NULL,
        kezdo_datum DATE NOT NULL,
        vegzo_datum DATE NOT NULL,
        innaktiv VARCHAR(128) NOT NULL,
    PRIMARY KEY (nyugta_id)
);