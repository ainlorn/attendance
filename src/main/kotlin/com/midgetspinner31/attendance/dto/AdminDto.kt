package com.midgetspinner31.attendance.dto

import com.midgetspinner31.attendance.enumerable.UserRole
import java.util.*

data class AdminDto(
    override var id: UUID? = null,
    override var fullName: String? = null,
    override var login: String? = null,
    override var email: String? = null,
    override var phone: String? = null
) : UserDto() {
    override var role: UserRole = UserRole.ADMIN
}
