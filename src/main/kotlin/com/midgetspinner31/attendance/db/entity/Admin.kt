package com.midgetspinner31.attendance.db.entity

import com.midgetspinner31.attendance.dto.AdminDto
import com.midgetspinner31.attendance.dto.UserDto
import com.midgetspinner31.attendance.enumerable.UserRole
import com.midgetspinner31.attendance.web.request.AdminSignUpRequest
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "admins")
@DiscriminatorValue(UserRole.ADMIN_VALUE)
class Admin : User(role = UserRole.ADMIN) {
    override fun toDto(): UserDto {
        return AdminDto(id, fullName, login, email, phone)
    }

    companion object {
        fun fromSignUpRequest(request: AdminSignUpRequest): Admin {
            return Admin().apply {
                this.login = request.login
                this.fullName = request.fullName
                this.email = request.email
                this.phone = request.phone
                this.password = request.password
            }
        }
    }
}
