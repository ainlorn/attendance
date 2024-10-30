package com.midgetspinner31.attendance.db.entity

import com.midgetspinner31.attendance.dto.StudentDto
import com.midgetspinner31.attendance.enumerable.UserRole
import com.midgetspinner31.attendance.web.request.StudentSignUpRequest
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Entity
@Table(name = "students")
@DiscriminatorValue(UserRole.STUDENT_VALUE)
class Student : User(role = UserRole.STUDENT) {
    @Column(name = "belt_level")
    var beltLevel: String? = null

    @Column(name = "birth_date", nullable = false)
    var birthDate: OffsetDateTime? = null

    @Column(name = "parent_full_name", nullable = false)
    var parentFullName: String? = null

    @Column(name = "parent_phone", nullable = false)
    var parentPhone: String? = null

    override fun toDto(): StudentDto {
        return StudentDto(id, fullName, login, email, phone, beltLevel, birthDate, parentFullName, parentPhone)
    }

    companion object {
        fun fromSignUpRequest(request: StudentSignUpRequest): Student {
            return Student().apply {
                this.login = request.login
                this.fullName = request.fullName
                this.email = request.email
                this.phone = request.phone
                this.password = request.password
                this.beltLevel = request.beltLevel
                this.birthDate = OffsetDateTime.of(request.birthDate, LocalTime.NOON, ZoneOffset.UTC)
                this.parentFullName = request.parentFullName
                this.parentPhone = request.parentPhone
            }
        }
    }
}
