package com.midgetspinner31.attendance.web.controller

import com.midgetspinner31.attendance.dto.StudentWithTransactionsDto
import com.midgetspinner31.attendance.dto.TransactionDto
import com.midgetspinner31.attendance.service.TransactionService
import com.midgetspinner31.attendance.web.annotation.ApiV1
import com.midgetspinner31.attendance.web.request.DepositRequest
import com.midgetspinner31.attendance.web.response.ItemResponse
import com.midgetspinner31.attendance.web.response.ListResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import java.time.OffsetDateTime
import java.util.*

@ApiV1
class TransactionController(
    private val transactionService: TransactionService
) {
    @GetMapping("/me/balance")
    fun myBalance(): ItemResponse<Long> {
        return ItemResponse(transactionService.getMyBalance())
    }

    @GetMapping("/groups/{groupId}/transactions")
    fun getGroupTransactions(
        @PathVariable groupId: UUID,
        @RequestParam(required = false) startTime: OffsetDateTime?,
        @RequestParam(required = false) endTime: OffsetDateTime?
    ): ListResponse<StudentWithTransactionsDto> {
        return ListResponse(transactionService.getTransactionsByGroup(groupId, startTime, endTime))
    }

    @GetMapping("/students/{studentId}/transactions")
    fun getStudentTransactions(
        @PathVariable studentId: UUID,
        @RequestParam(required = false) startTime: OffsetDateTime?,
        @RequestParam(required = false) endTime: OffsetDateTime?
    ): ListResponse<TransactionDto> {
        return ListResponse(transactionService.getTransactionsByStudent(studentId, startTime, endTime))
    }

    /**
     * Только для тестирования, потом удалить
     */
    @PostMapping("/deposit_test")
    fun depositTest(@Valid @RequestBody request: DepositRequest): ItemResponse<TransactionDto> {
        return ItemResponse(transactionService.depositTest(request.studentId!!, request.sum!!))
    }

}
