CREATE TABLE group_memberships
(
    id         UUID    NOT NULL,
    student_id UUID,
    group_id   UUID,
    start_date TIMESTAMP with time zone,
    active     BOOLEAN NOT NULL,
    CONSTRAINT pk_group_memberships PRIMARY KEY (id)
);

CREATE TABLE groups
(
    id         UUID NOT NULL,
    name       TEXT NOT NULL,
    trainer_id UUID,
    CONSTRAINT pk_groups PRIMARY KEY (id)
);

ALTER TABLE groups
    ADD CONSTRAINT FK_GROUPS_ON_TRAINER FOREIGN KEY (trainer_id) REFERENCES trainers (id);

ALTER TABLE group_memberships
    ADD CONSTRAINT FK_GROUP_MEMBERSHIPS_ON_GROUP FOREIGN KEY (group_id) REFERENCES groups (id);

ALTER TABLE group_memberships
    ADD CONSTRAINT FK_GROUP_MEMBERSHIPS_ON_STUDENT FOREIGN KEY (student_id) REFERENCES students (id);
