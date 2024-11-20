package com.midgetspinner31.attendance.dto

import java.time.OffsetDateTime
import java.util.*

data class LessonWithGroupDto(
    val id: UUID? = null,
    val groupId: UUID? = null,
    val startTime: OffsetDateTime? = null,
    val endTime: OffsetDateTime? = null
)
