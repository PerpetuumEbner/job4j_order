CREATE TABLE IF NOT EXISTS statuses
(
    id     SERIAL PRIMARY KEY,
    status TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS orders
(
    id        SERIAL PRIMARY KEY,
    status_id INT NOT NULL REFERENCES statuses (id)
);