CREATE TABLE IF NOT EXISTS Person (
    id         INTEGER  PRIMARY KEY AUTO_INCREMENT,
    version    INTEGER NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    house_id   INTEGER,
    age        INTEGER  NOT NULL
);

CREATE TABLE IF NOT EXISTS House (
    id         INTEGER  PRIMARY KEY AUTO_INCREMENT,
    version    INTEGER NOT NULL,
    address    VARCHAR(50) NOT NULL
);

CREATE INDEX IX_Person_House_Id ON Person (house_id);
ALTER TABLE Person ADD FOREIGN KEY (house_id) REFERENCES House(id);