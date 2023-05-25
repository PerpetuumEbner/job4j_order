CREATE TABLE IF NOT EXISTS statuses
(
    id     SERIAL PRIMARY KEY,
    status TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS orders
(
    id        SERIAL PRIMARY KEY,
    status_id INT NOT NULL REFERENCES statuses (id) DEFAULT 1
);

CREATE TABLE IF NOT EXISTS dishes
(
    id          SERIAL PRIMARY KEY,
    name        TEXT NOT NULL UNIQUE,
    description TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS orders_dishes
(
    id        SERIAL PRIMARY KEY,
    order_id  INT NOT NULL REFERENCES orders (id),
    dishes_id INT NOT NULL REFERENCES dishes (id)
);

CREATE TABLE IF NOT EXISTS notifications
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL
)