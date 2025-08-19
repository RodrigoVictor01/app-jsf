CREATE TABLE pessoa (
    id INTEGER PRIMARY KEY,
    nome VARCHAR(255),
    cidade VARCHAR(255),
    email VARCHAR(255),
    cep VARCHAR(20),
    enderco VARCHAR(255),
    pais VARCHAR(100),
    usuario VARCHAR(100),
    telefone VARCHAR(50),
    data_nascimento VARCHAR(20),
    cargo_id INTEGER
);

CREATE TABLE cargo (
    id INTEGER PRIMARY KEY,
    nome VARCHAR(255)
);

CREATE TABLE vencimentos (
    id INTEGER PRIMARY KEY,
    descricao VARCHAR(255),
    valor INTEGER,
    tipo VARCHAR(20)
);

CREATE TABLE cargo_vencimentos (
    id INTEGER PRIMARY KEY,
    cargo_id INTEGER,
    vencimento_id INTEGER
);

CREATE TABLE pessoa_salario_consolidado (
    pessoa_id INTEGER,
    nome_pessoa VARCHAR(255),
    nome_cargo VARCHAR(255),
    salario NUMERIC
);