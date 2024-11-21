package com.midgetspinner31.attendance.db.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "group_memberships", indexes = [
    Index(name = "idx_groupmembership_group_id", columnList = "group_id"),
    Index(name = "idx_groupmembership_student_id", columnList = "student_id"),
    Index(name = "idx_groupmembership_start_date", columnList = "start_date"),
    Index(name = "idx_groupmembership_end_date", columnList = "end_date")
])
class GroupMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID? = null

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    var student: Student? = null

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    var group: Group? = null

    @Column(name = "start_date", updatable = false)
    @CreationTimestamp
    var startDate: OffsetDateTime? = null

    @Column(name = "end_date")
    var endDate: OffsetDateTime? = null

    @Column(name = "active", nullable = false)
    var active: Boolean? = null
}
