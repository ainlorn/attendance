package com.midgetspinner31.attendance.dto

import java.util.*

data class StudentPublicDto(
    var id: UUID? = null,
    var fullName: String? = null,
    var beltLevel: String? = null
)
