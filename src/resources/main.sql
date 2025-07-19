
CREATE TABLE Horses (

    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    breed TEXT,
    -- Ny kolumn: ålder på hästen
    age INTEGER,
    box TEXT,
    gender TEXT,
    comments TEXT

);

-- Tabell för skötselanteckningar
CREATE TABLE IF NOT EXISTS CareNotes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    horse_id INTEGER NOT NULL,
    note TEXT NOT NULL,
    care_date DATE NOT NULL,
    FOREIGN KEY (horse_id) REFERENCES Horses(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Genders (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS feeding (

    id INTEGER PRIMARY KEY AUTOINCREMENT,
    horse_id INTEGER NOT NULL,
    horse_name TEXT NOT NULL,
    feed_type TIME NOT NULL,
    amount REAL NOT NULL,
    feed_time TEXT NOT NULL,
    comments TEXT,
    FOREIGN KEY (horse_id) REFERENCES Horses(id)

);

CREATE TABLE IF NOT EXISTS HorseImage (

    id INTEGER PRIMARY KEY AUTOINCREMENT,
    horse_id INTEGER NOT NULL,
    image_path TEXT NOT NULL,
    FOREIGN KEY (horse_id) REFERENCES Horses(id)

);



INSERT INTO Genders (name) VALUES ('Stallion');
INSERT INTO Genders (name) VALUES ('Mare');
INSERT INTO Genders (name) VALUES ('Gelding');
INSERT INTO Genders (name) VALUES ('Colt');
INSERT INTO Genders (name) VALUES ('Filly');
INSERT INTO Genders (name) VALUES ('Foal');
INSERT INTO Genders (name) VALUES ('Unknown');

