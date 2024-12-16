package com.midgetspinner31.attendance.service

import com.midgetspinner31.attendance.dto.StudentWithTransactionsDto
import com.midgetspinner31.attendance.dto.TransactionDto
import org.springframework.security.access.prepost.PreAuthorize
import java.time.OffsetDateTime
import java.util.*

interface TransactionService {
    @PreAuthorize("@userService.isStudent()")
    fun getMyBalance(): Long

    @PreAuthorize("isAuthenticated()")
    fun getBalanceByStudent(studentId: UUID): Long

    @PreAuthorize("isAuthenticated()")
    fun getBalanceByStudentOnTimestamp(studentId: UUID, timestamp: OffsetDateTime): Long

    @PreAuthorize("isAuthenticated()")
    fun getTransactionsByStudent(studentId: UUID, startTime: OffsetDateTime?, endTime: OffsetDateTime?): StudentWithTransactionsDto

    @PreAuthorize("@groupMembershipService.currentUserHasWriteAccess(#groupId)")
    fun getTransactionsByGroup(groupId: UUID, startTime: OffsetDateTime?, endTime: OffsetDateTime?): List<StudentWithTransactionsDto>

    fun depositTest(studentId: UUID, sum: Long): TransactionDto
}
