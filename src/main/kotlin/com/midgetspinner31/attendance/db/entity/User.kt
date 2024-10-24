package com.midgetspinner31.attendance.db.entity

import com.midgetspinner31.attendance.dto.UserDto
import com.midgetspinner31.attendance.enumerable.UserRole
import com.midgetspinner31.attendance.web.request.AdminSignUpRequest
import com.midgetspinner31.attendance.web.request.SignUpRequest
import com.midgetspinner31.attendance.web.request.StudentSignUpRequest
import com.midgetspinner31.attendance.web.request.TrainerSignUpRequest
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
abstract class User(
    @Transient val role: UserRole
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID? = null

    @Column(name = "full_name", nullable = false)
    var fullName: String? = null

    @Column(name = "login", nullable = false, unique = true)
    var login: String? = null

    @Column(name = "email", nullable = false, unique = true)
    var email: String? = null

    @Column(name = "phone", nullable = false, unique = true)
    var phone: String? = null

    @Column(name = "password", nullable = false)
    var password: String? = null

    abstract fun toDto() : UserDto

    companion object {
        fun fromSignUpRequest(request: SignUpRequest): User {
            return when (request) {
                is StudentSignUpRequest -> Student.fromSignUpRequest(request)
                is AdminSignUpRequest -> Admin.fromSignUpRequest(request)
                is TrainerSignUpRequest -> Trainer.fromSignUpRequest(request)
                else -> throw IllegalStateException()
            }
        }
    }
}
