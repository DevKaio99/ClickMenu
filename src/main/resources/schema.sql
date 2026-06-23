CREATE TABLE IF NOT EXISTS usuario (
    id                     UUID         PRIMARY KEY,
    nome                   VARCHAR(255) NOT NULL,
    email                  VARCHAR(255) NOT NULL UNIQUE,
    senha                  VARCHAR(255) NOT NULL,
    data_ultima_alteracao  TIMESTAMP,
    tipo                   VARCHAR(50)  NOT NULL
    );