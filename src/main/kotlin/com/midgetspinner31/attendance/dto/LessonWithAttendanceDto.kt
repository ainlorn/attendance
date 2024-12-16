package com.midgetspinner31.attendance.dto

data class LessonWithAttendanceDto(
    val lesson: LessonDto? = null,
    val attendance: List<StudentAttendanceDto>? = null
)
