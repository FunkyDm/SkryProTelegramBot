--luquibase formatted sql

--changeset Dm:1

CREATE TABLE tasks (
    task_id INT PRIMARY KEY,
    chat_id INT,
    message_text VARCHAR,
    task_date DATE,
    task_time TIME
);