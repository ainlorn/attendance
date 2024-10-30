package com.midgetspinner31.attendance.db.entity

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "group_memberships")
class GroupMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    val id: UUID? = null

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    val student: Student? = null

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    val group: Group? = null

    @Column(name = "start_date", insertable = false, updatable = false)
    val startDate: OffsetDateTime = OffsetDateTime.now()
}