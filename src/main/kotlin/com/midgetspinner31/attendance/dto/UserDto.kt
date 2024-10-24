package com.midgetspinner31.attendance.dto

import com.midgetspinner31.attendance.enumerable.UserRole
import java.util.*


abstract class UserDto {
    abstract var id: UUID?
    abstract var role: UserRole
    abstract var fullName: String?
    abstract var login: String?
    abstract var email: String?
    abstract var phone: String?
}
