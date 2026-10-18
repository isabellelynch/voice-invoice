CREATE TABLE customer (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(120) NOT NULL,
    email       VARCHAR(200),
    address     VARCHAR(300),
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE invoice (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id      BIGINT NOT NULL REFERENCES customer(id),
    status           VARCHAR(20) NOT NULL,          -- always DRAFT in this prototype
    idempotency_key  VARCHAR(64) UNIQUE,            -- stops one click creating two drafts
    net              DECIMAL(12, 2) NOT NULL,
    tax              DECIMAL(12, 2) NOT NULL,
    total            DECIMAL(12, 2) NOT NULL,
    created_at       TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE invoice_line (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id   BIGINT NOT NULL REFERENCES invoice(id),
    line_no      INT NOT NULL,
    description  VARCHAR(300) NOT NULL,
    quantity     DECIMAL(10, 3) NOT NULL,
    unit_price   DECIMAL(12, 2) NOT NULL,
    tax_rate     VARCHAR(20) NOT NULL,
    net          DECIMAL(12, 2) NOT NULL,
    tax          DECIMAL(12, 2) NOT NULL
);
