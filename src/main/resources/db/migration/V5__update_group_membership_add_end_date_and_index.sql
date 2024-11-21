ALTER TABLE group_memberships
    ADD end_date TIMESTAMP with time zone;

CREATE INDEX idx_groupmembership_end_date ON group_memberships (end_date);

CREATE INDEX idx_groupmembership_group_id ON group_memberships (group_id);

CREATE INDEX idx_groupmembership_start_date ON group_memberships (start_date);

CREATE INDEX idx_groupmembership_student_id ON group_memberships (student_id);
