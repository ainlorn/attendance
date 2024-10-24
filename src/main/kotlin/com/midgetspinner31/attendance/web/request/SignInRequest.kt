package com.midgetspinner31.attendance.web.request

import jakarta.validation.constraints.NotEmpty

class SignInRequest(
    username: String? = null,
    password: String? = null
) : ApiRequest() {
    @NotEmpty
    var username = trim(username)
        set(value) { field = trim(value) }

    @NotEmpty
    var password = password
}
