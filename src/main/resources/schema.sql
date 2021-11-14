CREATE TABLE users (
    id INT NOT NULL,
    name CHAR NOT NULL
);
CREATE TABLE matches (
    id INT NOT NULL,
    user1 INT NOT NULL,
    user2 INT NOT NULL,
    user1Hand CHAR NOT NULL,
    user2Hand CHAR NOT NULL
);
