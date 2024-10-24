package com.midgetspinner31.attendance.web.request

import com.midgetspinner31.attendance.validation.constraint.PhoneNumber
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import java.time.LocalDate

class StudentSignUpRequest(
    login: String? = null,
    fullName: String? = null,
    email: String? = null,
    phone: String? = null,
    password: String? = null,
    beltLevel: String? = null,
    birthDate: LocalDate? = null,
    parentFullName: String? = null,
    parentPhone: String? = null
) : SignUpRequest(login, fullName, email, phone, password) {
    @Pattern(regexp = "^\\w{1,32}$")
    var beltLevel = trim(beltLevel)
        set(value) { field = trim(value) }

    @Pattern(regexp = "^[а-яА-Я- ]{8,100}$")
    @NotEmpty
    var parentFullName = trim(parentFullName)
        set(value) { field = trim(value) }

    @PhoneNumber
    @NotEmpty
    var parentPhone = trim(parentPhone)
        set(value) { field = trim(value) }

    @NotNull
    var birthDate = birthDate

}
