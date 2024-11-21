CREATE TABLE lesson_attendances
(
    id         UUID NOT NULL,
    student_id UUID NOT NULL,
    lesson_id  UUID NOT NULL,
    CONSTRAINT pk_lesson_attendances PRIMARY KEY (id)
);

ALTER TABLE lesson_attendances
    ADD CONSTRAINT uc_lessonattendance UNIQUE (student_id, lesson_id);

ALTER TABLE lesson_attendances
    ADD CONSTRAINT FK_LESSON_ATTENDANCES_ON_LESSON FOREIGN KEY (lesson_id) REFERENCES lessons (id);

CREATE INDEX idx_lessonattendance_lesson_id ON lesson_attendances (lesson_id);

ALTER TABLE lesson_attendances
    ADD CONSTRAINT FK_LESSON_ATTENDANCES_ON_STUDENT FOREIGN KEY (student_id) REFERENCES students (id);

CREATE INDEX idx_lessonattendance_student_id ON lesson_attendances (student_id);
