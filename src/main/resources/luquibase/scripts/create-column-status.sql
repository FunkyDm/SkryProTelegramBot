--luquibase formatted sql

--changeset Dm:10

ALTER TABLE tasks ADD status VARCHAR(50) DEFAULT 'no';