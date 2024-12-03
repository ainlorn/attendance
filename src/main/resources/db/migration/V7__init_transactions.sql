CREATE TABLE transactions
(
    id               UUID                     NOT NULL,
    student_id       UUID                     NOT NULL,
    transaction_type TEXT                     NOT NULL,
    key              UUID,
    sum              BIGINT                   NOT NULL,
    dt               TIMESTAMP with time zone NOT NULL,
    CONSTRAINT pk_transactions PRIMARY KEY (id)
);

ALTER TABLE transactions
    ADD CONSTRAINT uc_transactions_dt UNIQUE (dt);

CREATE INDEX idx_transaction_dt ON transactions (dt);

ALTER TABLE transactions
    ADD CONSTRAINT FK_TRANSACTIONS_ON_STUDENT FOREIGN KEY (student_id) REFERENCES students (id);

CREATE INDEX idx_transaction_student_id ON transactions (student_id);

CREATE UNIQUE INDEX uc_transaction_transaction_type_key ON transactions (transaction_type, key) WHERE key IS NOT NULL;
