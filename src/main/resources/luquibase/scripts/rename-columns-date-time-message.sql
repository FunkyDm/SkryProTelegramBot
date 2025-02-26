--luquibase formatted sql

--changeset Dm:2

ALTER TABLE tasks RENAME COLUMN task_date TO date_time;

ALTER TABLE tasks RENAME COLUMN message_text TO message;