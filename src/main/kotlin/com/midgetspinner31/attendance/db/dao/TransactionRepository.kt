package com.midgetspinner31.attendance.db.dao

import com.midgetspinner31.attendance.db.entity.Transaction
import com.midgetspinner31.attendance.enumerable.TransactionType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.OffsetDateTime
import java.util.*

interface TransactionRepository : JpaRepository<Transaction, UUID> {

    fun findByTransactionTypeAndKey(transactionType: TransactionType, key: UUID): Transaction?

    fun findByStudentId(studentId: UUID): List<Transaction>

    @Query("select t from Transaction t where t.student.id = :studentId and t.dt between :startTime and :endTime")
    fun findByStudentIdInTimeInterval(studentId: UUID, startTime: OffsetDateTime, endTime: OffsetDateTime): List<Transaction>

    @Query("select coalesce(sum(t.sum), 0) from Transaction t where t.student.id=:studentId")
    fun getBalanceByStudentId(studentId: UUID): Long

}
