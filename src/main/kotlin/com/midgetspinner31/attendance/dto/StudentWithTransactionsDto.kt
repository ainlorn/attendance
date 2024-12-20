package com.midgetspinner31.attendance.dto

data class StudentWithTransactionsDto(
    val student: StudentPublicDto,
    val transactions: List<TransactionDto>,
    val startBalance: Long,
    val endBalance: Long
)
