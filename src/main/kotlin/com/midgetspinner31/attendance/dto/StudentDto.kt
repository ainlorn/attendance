package com.midgetspinner31.attendance.dto

import com.midgetspinner31.attendance.enumerable.UserRole
import java.time.OffsetDateTime
import java.util.*

data class StudentDto(
    override var id: UUID? = null,
    override var fullName: String? = null,
    override var login: String? = null,
    override var email: String? = null,
    override var phone: String? = null,
    var beltLevel: String? = null,
    var birthDate: OffsetDateTime? = null,
    var parentFullName: String? = null,
    var parentPhone: String? = null
) : UserDto() {
    override var role: UserRole = UserRole.STUDENT
}
