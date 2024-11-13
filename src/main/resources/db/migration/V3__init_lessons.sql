CREATE TABLE lessons
(
    id         UUID                     NOT NULL,
    group_id   UUID                     NOT NULL,
    start_time TIMESTAMP with time zone NOT NULL,
    end_time   TIMESTAMP with time zone NOT NULL,
    CONSTRAINT pk_lessons PRIMARY KEY (id)
);

CREATE INDEX idx_lesson_end_time ON lessons (end_time);

CREATE INDEX idx_lesson_start_time ON lessons (start_time);

ALTER TABLE lessons
    ADD CONSTRAINT FK_LESSONS_ON_GROUP FOREIGN KEY (group_id) REFERENCES groups (id);

CREATE INDEX idx_lesson_group_id ON lessons (group_id);
