-- liquibase formatted sql

-- changeset funkyDM:1
CREATE TABLE tasks (
    ID SERIAL,
    chat_ID long,
    message String,

)