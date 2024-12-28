--luquibase formatted sql

--changeset Dm:4

ALTER TABLE tasks RENAME COLUMN date_time TO task_date;