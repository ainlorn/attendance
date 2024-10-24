package com.midgetspinner31.attendance.web.request

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.midgetspinner31.attendance.enumerable.UserRole
import com.midgetspinner31.attendance.validation.constraint.PhoneNumber
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "role",
    visible = true
)
@JsonSubTypes(
    JsonSubTypes.Type(value = StudentSignUpRequest::class, name = UserRole.STUDENT_VALUE),
    JsonSubTypes.Type(value = TrainerSignUpRequest::class, name = UserRole.TRAINER_VALUE),
    JsonSubTypes.Type(value = AdminSignUpRequest::class, name = UserRole.ADMIN_VALUE)
)
abstract class SignUpRequest(
    login: String?,
    fullName: String?,
    email: String?,
    phone: String?,
    password: String?
) : ApiRequest() {
    @Pattern(regexp = "^[a-zA-Z-_\\d]{3,32}$")
    @NotEmpty
    var login = trim(login)
        set(value) { field = trim(value) }

    @Pattern(regexp = "^[а-яА-Я- ]{8,100}$")
    @NotEmpty
    var fullName = trim(fullName)
        set(value) { field = trim(value) }

    @Email
    @NotEmpty
    var email = trim(email)
        set(value) { field = trim(value) }

    @PhoneNumber
    @NotEmpty
    var phone = trim(phone)
        set(value) { field = trim(value) }

    @Size(min = 8, max = 72)
    @NotEmpty
    var password = password
}
