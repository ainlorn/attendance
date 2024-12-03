package com.midgetspinner31.attendance.dto

import com.midgetspinner31.attendance.enumerable.TransactionType
import java.time.OffsetDateTime
import java.util.*

data class TransactionDto(
    val id: UUID? = null,
    val type: TransactionType? = null,
    val dt: OffsetDateTime? = null,
    val sum: Long? = null
)
