package com.midgetspinner31.attendance.db.entity

import com.midgetspinner31.attendance.dto.TransactionDto
import com.midgetspinner31.attendance.enumerable.TransactionType
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "transactions", indexes = [
    Index(name = "idx_transaction_student_id", columnList = "student_id"),
    Index(name = "idx_transaction_dt", columnList = "dt")
])
class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID? = null

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    var student: Student? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    var transactionType: TransactionType? = null

    @Column(name = "key")
    var key: UUID? = null

    @Column(name = "sum", nullable = false)
    var sum: Long? = null

    @Column(name = "dt", nullable = false, unique = true)
    var dt: OffsetDateTime? = null

    fun toDto(): TransactionDto {
        return TransactionDto(id, transactionType, dt, sum)
    }
}
