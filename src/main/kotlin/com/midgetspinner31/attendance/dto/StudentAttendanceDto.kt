package com.midgetspinner31.attendance.dto

import java.util.*

data class StudentAttendanceDto(
    val studentFullName: String? = null,
    val studentId: UUID? = null,
    val attended: Boolean? = null
)
