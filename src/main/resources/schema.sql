CREATE TABLE IF NOT EXISTS usuario (
    id                     UUID             PRIMARY KEY,
    nome                   VARCHAR(255)     NOT NULL,
    email                  VARCHAR(255)     NOT NULL UNIQUE,
    senha                  VARCHAR(255)     NOT NULL,
    data_ultima_alteracao  TIMESTAMP,
    tipo                   VARCHAR(50)  NOT NULL
    );

CREATE TABLE IF NOT EXISTS restaurante (
    id                      UUID            PRIMARY KEY,
    nome_restaurante        VARCHAR(255)    NOT NULL,
    endereco_restaurante    VARCHAR(255)    NOT NULL,
    tipo_cozinha            VARCHAR(50)     NOT NULL,
    horario_abertura        TIMESTAMP       NOT NULL,
    horario_fechamento      TIMESTAMP       NOT NULL,
    dias_funcionamento      VARCHAR(50)     NOT NULL,
    dono_restaurante        UUID            NOT NULL,

    CONSTRAINT fk_restaurante_usuario FOREIGN KEY (dono_restaurante) REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS item_cardapio (
    id                      UUID            PRIMARY KEY,
    nome                    VARCHAR(255)    NOT NULL,
    descricao               VARCHAR(255)    NOT NULL,
    preco                   DECIMAL(10,2)   NOT NULL,
    consumir_apenas_restaurante    BOOLEAN       NOT NULL,
    foto                    VARCHAR(255)    NOT NULL,
    restaurante_id             UUID            NOT NULL,

    CONSTRAINT fk_item_cardapio_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurante(id)
);