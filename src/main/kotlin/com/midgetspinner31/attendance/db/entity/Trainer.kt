package com.midgetspinner31.attendance.db.entity

import com.midgetspinner31.attendance.dto.TrainerDto
import com.midgetspinner31.attendance.enumerable.UserRole
import com.midgetspinner31.attendance.web.request.TrainerSignUpRequest
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "trainers")
@DiscriminatorValue(UserRole.TRAINER_VALUE)
class Trainer : User(role = UserRole.TRAINER) {
    override fun toDto(): TrainerDto {
        return TrainerDto(id, fullName, login, email, phone)
    }

    companion object {
        fun fromSignUpRequest(request: TrainerSignUpRequest): Trainer {
            return Trainer().apply {
                this.login = request.login
                this.fullName = request.fullName
                this.email = request.email
                this.phone = request.phone
                this.password = request.password
            }
        }
    }
}
