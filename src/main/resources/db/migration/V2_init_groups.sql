CREATE TABLE groups
(
    id   UUID         NOT NULL,
    name varchar(255) NOT NULL,
    trainer_id UUID,
    CONSTRAINT pk_groups PRIMARY KEY (id),
    CONSTRAINT fk_trainer FOREIGN KEY (trainer_id) REFERENCES trainers (id)

);

CREATE TABLE group_memberships(
      id         UUID NOT NULL,
      student_id UUID NOT NULL,
      group_id   UUID NOT NULL,
      start_date TIMESTAMP WITH TIME ZONE NOT NULL,
      CONSTRAINT pk_group_memberships PRIMARY KEY (id),
      CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES students (id),
      CONSTRAINT fk_group FOREIGN KEY (group_id) REFERENCES groups (id)
)