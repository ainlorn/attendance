package com.midgetspinner31.attendance.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.midgetspinner31.attendance.enumerable.BillStatus
import java.time.OffsetDateTime
import java.util.*

data class BillDto(
    val id: UUID? = null,
    @JsonIgnore val filename: String? = null,
    @JsonIgnore val contentType: String? = null,
    val url: String? = null,
    val student: StudentPublicDto? = null,
    val status: BillStatus? = null,
    val amount: Long? = null,
    val createdDt: OffsetDateTime? = null
)
