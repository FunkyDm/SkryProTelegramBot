--luquibase formatted sql

--changeset Dm:9

CREATE TABLE tasks (
    task_id SERIAL PRIMARY KEY,
    chat_id TEXT NOT NULL,
    message TEXT NOT NULL,
    task_date TIMESTAMP NOT NULL
   );