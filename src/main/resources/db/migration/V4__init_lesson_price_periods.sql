CREATE TABLE lesson_price_periods
(
    id         UUID                     NOT NULL,
    group_id   UUID                     NOT NULL,
    start_time TIMESTAMP with time zone NOT NULL,
    end_time   TIMESTAMP with time zone NOT NULL,
    price      BIGINT                   NOT NULL,
    CONSTRAINT pk_lesson_price_periods PRIMARY KEY (id)
);

ALTER TABLE lesson_price_periods
    ADD CONSTRAINT FK_LESSON_PRICE_PERIODS_ON_GROUP FOREIGN KEY (group_id) REFERENCES groups (id);
