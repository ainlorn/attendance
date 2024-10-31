package com.midgetspinner31.attendance.db.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "group_memberships")
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

    @Column(name = "active", nullable = false)
    var active: Boolean? = null
}
