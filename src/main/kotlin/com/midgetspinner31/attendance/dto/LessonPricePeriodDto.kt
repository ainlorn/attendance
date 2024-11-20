package com.midgetspinner31.attendance.dto

import java.time.OffsetDateTime
import java.util.*

data class LessonPricePeriodDto(
    val id: UUID? = null,
    val startTime: OffsetDateTime? = null,
    val endTime: OffsetDateTime? = null,
    val price: Long? = null
)
