package com.midgetspinner31.attendance.service.impl

import com.midgetspinner31.attendance.db.dao.GroupMembershipRepository
import com.midgetspinner31.attendance.db.dao.StudentRepository
import com.midgetspinner31.attendance.db.dao.TransactionRepository
import com.midgetspinner31.attendance.db.dao.UserRepository
import com.midgetspinner31.attendance.db.entity.Transaction
import com.midgetspinner31.attendance.dto.StudentWithTransactionsDto
import com.midgetspinner31.attendance.dto.TransactionDto
import com.midgetspinner31.attendance.enumerable.TransactionType
import com.midgetspinner31.attendance.enumerable.UserRole
import com.midgetspinner31.attendance.exception.AccessDeniedException
import com.midgetspinner31.attendance.exception.UserNotFoundException
import com.midgetspinner31.attendance.service.TransactionService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.*

@Service
class TransactionServiceImpl(
    private val userRepository: UserRepository,
    private val studentRepository: StudentRepository,
    private val transactionRepository: TransactionRepository,
    private val groupMembershipRepository: GroupMembershipRepository
) : TransactionService {
    override fun getMyBalance(): Long {
        return transactionRepository.getBalanceByStudentId(userRepository.getCurrentUser()!!.id!!)
    }

    override fun getTransactionsByStudent(
        studentId: UUID,
        startTime: OffsetDateTime?,
        endTime: OffsetDateTime?
    ): List<TransactionDto> {
        val currentUser = userRepository.getCurrentUser()!!
        val user = userRepository.findByIdOrNull(studentId) ?: throw UserNotFoundException()

        if (user.role != UserRole.STUDENT || currentUser.id!! != user.id!! && currentUser.role != UserRole.TRAINER) {
            throw AccessDeniedException()
        }

        return if (startTime != null && endTime != null) {
            transactionRepository.findByStudentIdInTimeInterval(studentId, startTime, endTime)
        } else {
            transactionRepository.findByStudentId(studentId)
        }.map { it.toDto() }
    }

    override fun getTransactionsByGroup(
        groupId: UUID,
        startTime: OffsetDateTime?,
        endTime: OffsetDateTime?
    ): List<StudentWithTransactionsDto> {
        return groupMembershipRepository
            .findAllWithInactiveByGroupId(groupId).map { it.student!! }
            .distinct()
            .map { student ->
                StudentWithTransactionsDto(
                    student.toPublicDto(),
                    if (startTime != null && endTime != null) {
                        transactionRepository.findByStudentIdInTimeInterval(student.id!!, startTime, endTime)
                    } else {
                        transactionRepository.findByStudentId(student.id!!)
                    }.map { it.toDto() }
                )
            }
    }

    @Transactional
    override fun depositTest(studentId: UUID, sum: Long): TransactionDto {
        val student = studentRepository.findByIdOrNull(studentId) ?: throw UserNotFoundException()
        return transactionRepository.save(Transaction().apply {
            this.student = student
            this.transactionType = TransactionType.DEPOSIT
            this.sum = sum
            this.dt = OffsetDateTime.now()
        }).toDto()
    }
}
