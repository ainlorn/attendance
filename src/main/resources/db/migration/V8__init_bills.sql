CREATE TABLE bills
(
    id           UUID                     NOT NULL,
    filename     TEXT                     NOT NULL,
    content_type TEXT                     NOT NULL,
    student_id   UUID                     NOT NULL,
    status       TEXT                     NOT NULL,
    amount       BIGINT                   NOT NULL,
    created_dt   TIMESTAMP with time zone NOT NULL,
    CONSTRAINT pk_bills PRIMARY KEY (id)
);

ALTER TABLE bills
    ADD CONSTRAINT FK_BILLS_ON_STUDENT FOREIGN KEY (student_id) REFERENCES students (id);
