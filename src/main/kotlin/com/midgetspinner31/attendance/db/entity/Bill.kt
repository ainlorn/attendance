package com.midgetspinner31.attendance.db.entity

import com.midgetspinner31.attendance.enumerable.BillStatus
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "bills")
class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID? = null

    @Column(name = "filename", nullable = false)
    var filename: String? = null

    @Column(name = "content_type", nullable = false)
    var contentType: String? = null

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    var student: Student? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: BillStatus? = null

    @Column(name = "amount", nullable = false)
    var amount: Long? = null

    @CreationTimestamp
    @Column(name = "created_dt", nullable = false, updatable = false)
    var createdDt: OffsetDateTime? = null
}
